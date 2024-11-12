package com.example.osmdemo.trips.presentation

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.osmdemo.BuildConfig
import com.example.osmdemo.core.backend.BackendResult
import com.example.osmdemo.databinding.FragmentTripsBinding
import com.example.osmdemo.map.data.model.Trip
import com.example.osmdemo.map.domain.repository.MapRepository
import com.example.osmdemo.trips_details.presentation.OnTripClickListener
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class TripsFragment : Fragment(), OnTripClickListener {

    private var _binding: FragmentTripsBinding? = null
    private val binding get() = _binding!!

    private val accessId = BuildConfig.API_KEY // Token del API

    @Inject
    lateinit var map: MapRepository

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTripsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupOnRefreshListener()
        setupOnSearchTrip()
    }

    private fun setupRecyclerView(trips: List<Trip>) {
        binding.rvTrips.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = TripsAdapter(trips, this@TripsFragment)
        }
    }

    private fun setupOnRefreshListener() {
        binding.swipeRefreshLayout.setOnRefreshListener {
            setupOnSearchTrip()
        }
    }

    private fun setupOnSearchTrip() {
        lifecycleScope.launch {
            val originName = "La Parrilla Moderna, Transversal 93, UPZ Álamos"
            val destName = "El Campin, Avenida Carrera 14, UPZ Chapinero"

            // Obtener ubicación de origen y destino
            val originLocation = map.searchLocationByName(
                input = originName,
                type = "S",
                accessId = accessId
            )

            val destLocation = map.searchLocationByName(
                input = destName,
                type = "S",
                accessId = accessId
            )

            if (originLocation is BackendResult.Success && destLocation is BackendResult.Success) {
                // Obtener listado de viajes de acuerdo al origen y destino
                val routesResult = map.searchTrip(
                    originId = "980035049",
                    //originCoordLat = 4687623,
                    //originCoordLong = -74119018,
                    //originCoordName = "La Parrilla Moderna",
                    destId = "980010843",
                    //destCoordLat = 4643881,
                    //destCoordLong = -74065460,
                    //destCoordName = "El Campin",
                    groupFilter = "GROUP_PT",
                    attributes = "!barrierfree",
                    products = 2304,
                    totalWalk = "1,0,2000,100",
                    totalBike = "1,0,0,100",
                    totalCar = "1,0,2000,100",
                    addChangeTime = 4,
                    changeTimePercent = 100,
                    maxChange = 11,
                    //addChangeTime = "",
                    //minChangeTime = 1,
                    //maxChangeTime = 50,
                    rtMode = "OFF",
                    poly = 1,
                    passlist = 1,
                    accessId = accessId
                )

                when (routesResult) {
                    is BackendResult.Success -> {
                        binding.swipeRefreshLayout.isRefreshing = false
                        setupRecyclerView(routesResult.data.trip)
                    }
                    is BackendResult.Error -> {
                        binding.swipeRefreshLayout.isRefreshing = false
                        Log.e("TAG", "Exception in fetching routes $routesResult")
                    }
                    is BackendResult.Exception -> {
                        binding.swipeRefreshLayout.isRefreshing = false
                        Log.e("TAG", "Exception in fetching routes $routesResult")
                    }
                }
            }

        }
    }

    override fun onTripClick(trip: Trip) {
        val action = TripsFragmentDirections.actionTripsFragmentToMapFragment(trip)
        //val action = TripsFragmentDirections.actionTripsFragmentToJourneyDetailsFragment(trip)
        findNavController().navigate(action)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }



}