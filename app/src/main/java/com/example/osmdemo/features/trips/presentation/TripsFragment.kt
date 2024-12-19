package com.example.osmdemo.features.trips.presentation

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.addCallback
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.osmdemo.databinding.FragmentTripsBinding
import com.example.osmdemo.core.backend.model.Trip
import com.example.osmdemo.features.options.presentation.OptionsViewModel
import com.example.osmdemo.features.trips_details.presentation.OnTripClickListener
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class TripsFragment : Fragment(), OnTripClickListener {

    private var _binding: FragmentTripsBinding? = null
    private val binding get() = _binding!!

    private val args by navArgs<TripsFragmentArgs>()

    private val viewModel by viewModels<TripsViewModel>()
    private val optionsViewModel by activityViewModels<OptionsViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTripsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onNavigationOnClick()
        setupOnBackPressed()
        setupOnRefreshListener()
        setupOnSearchTrip()
        setupTripsState()
    }

    private fun setupTripsState() {
        lifecycleScope.launch {
            viewModel.state.collectLatest { state ->
                when {
                    state.errorMessage != null -> {
                        Toast.makeText(
                            requireContext(),
                            state.errorMessage,
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                    state.exception != null -> {
                        Toast.makeText(
                            requireContext(),
                            state.exception.message,
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                    state.isLoading -> { isLoading() }

                    state.trips.isNotEmpty() -> { setupRecyclerView(state.trips) }
                }
            }
        }
    }

    /**
     * Click de botón de navegación
     * */
    private fun onNavigationOnClick() {
        binding.toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
    }

    // Botón de retroceso
    private fun setupOnBackPressed() {
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            findNavController().popBackStack()
        }
    }

    private fun isLoading() {
        binding.progressCircular.visibility = View.VISIBLE
        binding.empty.visibility = View.GONE
        binding.linearLayoutContainer.visibility = View.GONE
    }

    private fun setupRecyclerView(trips: List<Trip>) {
        if (trips.isNotEmpty()) {
            binding.swipeRefreshLayout.isRefreshing = false
            binding.linearLayoutContainer.visibility = View.VISIBLE
            binding.progressCircular.visibility = View.GONE
            binding.empty.visibility = View.GONE
            binding.rvTrips.apply {
                layoutManager = LinearLayoutManager(requireContext())
                adapter = TripsAdapter(trips ?: listOf(), this@TripsFragment)
            }
        } else {
            binding.swipeRefreshLayout.isRefreshing = false
            binding.linearLayoutContainer.visibility = View.GONE
            binding.progressCircular.visibility = View.GONE
            binding.empty.visibility = View.VISIBLE
        }
    }

    private fun setupOnRefreshListener() {
        binding.swipeRefreshLayout.setOnRefreshListener(::setupOnSearchTrip)
    }

    private fun setupOnSearchTrip() {
        lifecycleScope.launch {
            val originCoordLong = args.originCoordLong
            val originCoordLat = args.originCoordLat
            val destCoordLong = args.destCoordLong
            val destCoordLat = args.destCoordLat
            val selectedOptions = optionsViewModel.state.value.selectedOptions
            val maxChange = selectedOptions.maxChanges
            val products = selectedOptions.products
            val attributes = selectedOptions.accessType.value
            val totalWalk = selectedOptions.totalWalk
            val totalBike = selectedOptions.totalBike
            val totalCar = selectedOptions.totalCar
            val changeTimePercent = selectedOptions.changeTimePercent
            val addChangeTime = selectedOptions.addChangeTime
            val searchForArrival = args.searchForArrival
            val time = args.time
            val date = args.date

            Log.d("TAG", "setupOnSearchTrip: originCoordLong -> $originCoordLong")
            Log.d("TAG", "setupOnSearchTrip: originCoordLat -> $originCoordLat")
            Log.d("TAG", "setupOnSearchTrip: destCoordLong -> $destCoordLong")
            Log.d("TAG", "setupOnSearchTrip: destCoordLat -> $destCoordLat")
            Log.d("TAG", "setupOnSearchTrip: maxChange -> $maxChange")
            Log.d("TAG", "setupOnSearchTrip: products -> $products")
            Log.d("TAG", "setupOnSearchTrip: attributes -> $attributes")
            Log.d("TAG", "setupOnSearchTrip: maxChange -> $maxChange")
            Log.d("TAG", "setupOnSearchTrip: totalWalk -> $totalWalk")
            Log.d("TAG", "setupOnSearchTrip: totalBike -> $totalBike")
            Log.d("TAG", "setupOnSearchTrip: totalCar -> $totalCar")
            Log.d("TAG", "setupOnSearchTrip: changeTimePercent -> $changeTimePercent")
            Log.d("TAG", "setupOnSearchTrip: addChangeTime -> $addChangeTime")
            Log.d("TAG", "setupOnSearchTrip: time -> $time")
            Log.d("TAG", "setupOnSearchTrip: date -> $date")
            Log.d("TAG", "setupOnSearchTrip: searchForArrival -> $searchForArrival")

            viewModel.onEvent(
                TripsEvent.OnSearchTrip(
                    //originId = startLocationId,
                    originCoordLat = originCoordLat.toDouble(),
                    originCoordLong = originCoordLong.toDouble(),
                    //originCoordName = "La Parrilla Moderna",
                    //destId = destinationLocationId,
                    destCoordLat = destCoordLat.toDouble(),
                    destCoordLong = destCoordLong.toDouble(),
                    //destCoordName = "El Campin",
                    time = time,
                    date = date,
                    groupFilter = "GROUP_PT",
                    attributes = attributes,
                    products = products,
                    totalWalk = totalWalk,
                    totalBike = totalBike,
                    totalCar = totalCar,
                    addChangeTime = 4,
                    //maxChangeTime = 10,
                    //minChangeTime = 1,
                    changeTimePercent = changeTimePercent,
                    maxChange = maxChange,
                    rtMode = "REALTIME",
                    poly = 1,
                    passlist = 1,
                    searchForArrival = searchForArrival
                )
            )
        }
    }

    override fun onTripClick(trip: Trip) {
        lifecycleScope.launch {
            try {
                Log.d("TAG", "onTripClick: trip -> $trip")
                val action = TripsFragmentDirections.actionTripsFragmentToMapFragment(trip)
                findNavController().navigate(action)
            } catch (e: Exception) {
                Log.e("TAG", "onTripClick: exception -> ${e.message}")
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}