package com.example.osmdemo.trips_details.presentation

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.osmdemo.BuildConfig
import com.example.osmdemo.core.backend.BackendResult
import com.example.osmdemo.databinding.FragmentTripsDetailsBinding
import com.example.osmdemo.map.data.model.Leg
import com.example.osmdemo.map.data.model.Trip
import com.example.osmdemo.map.domain.repository.MapRepository
import com.example.osmdemo.map.presentation.MapFragmentDirections
import com.example.osmdemo.trips.presentation.OnLegClickListener
import com.example.osmdemo.trips_details.presentation.adapters.TripsDetailsAdapter
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class TripsDetailsFragment : BottomSheetDialogFragment(), OnLegClickListener {

    private var _binding: FragmentTripsDetailsBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var map: MapRepository

    private val args: TripsDetailsFragmentArgs by navArgs()

    private val gisRoute = mutableListOf<Trip>()

    private val accessId = BuildConfig.API_KEY // Token del API

    companion object {
        fun newInstance(trip: Trip): TripsDetailsFragment {
            val fragment = TripsDetailsFragment()
            val args = Bundle().apply { putParcelable("trip", trip) }
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTripsDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupOnGisRoute(args.trip)
        setupOnBackPressed()
    }

    private fun setupOnGisRoute(trip: Trip) {
        lifecycleScope.launch {
            trip.legList.leg.forEach { leg ->
                when(leg.type) {
                    "WALK" -> {
                        val result = map.getGISRoute(
                            ctx = leg.gisRef!!.ref,
                            poly = 1,
                            accessId = accessId
                        )
                        when (result) {
                            is BackendResult.Success -> { result.data.trip.forEach { gisRoute.add(it) } }
                            is BackendResult.Error -> Log.e("TAG", "Error fetching GIS route")
                            is BackendResult.Exception -> Log.e("TAG", "Exception fetching GIS route")
                        }
                    }
                }
            }
            setupOnRecyclerView()
        }
    }

    private fun setupOnRecyclerView() {
        val legs = args.trip.legList.leg
        val gis = gisRoute

        binding.rvJourneyDetails.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = TripsDetailsAdapter(
                legs = legs,
                gis = gis,
                listener = this@TripsDetailsFragment
            )
        }
    }

    private fun setupOnBackPressed() {
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            findNavController().popBackStack()
        }
    }

    override fun onLegClick(leg: Leg) {
        val journeyDetailRef = leg.journeyDetailRef?.ref
        if (journeyDetailRef != null) {
            findNavController().navigate(
                MapFragmentDirections.actionMapFragmentToJourneyDetailsFragment(
                    ref = journeyDetailRef
                )
            )
            dismiss()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}