package com.example.osmdemo.features.trips_details.presentation

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.osmdemo.BuildConfig
import com.example.osmdemo.core.backend.utils.BackendResult
import com.example.osmdemo.databinding.FragmentTripsDetailsBinding
import com.example.osmdemo.core.backend.model.Leg
import com.example.osmdemo.core.backend.model.Trip
import com.example.osmdemo.features.map.domain.MapRepository
import com.example.osmdemo.features.map.presentation.MapFragmentDirections
import com.example.osmdemo.features.trips.presentation.OnLegClickListener
import com.example.osmdemo.features.trips_details.presentation.adapters.TripsDetailsAdapter
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class TripsDetailsFragment : BottomSheetDialogFragment() {

    private var _binding: FragmentTripsDetailsBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModels<TripsDetailsViewModel>()

    private val args: TripsDetailsFragmentArgs by navArgs()

    private val gisRoute = mutableListOf<Trip>()

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
            /*trip.legList.leg.forEach { leg ->
                when(leg.type) {
                    "WALK" -> {
                        if (leg.gisRef?.ref != null) {
                            viewModel.onEvent(
                                TripsDetailsEvent.RetrieveGisRoute(ctx = leg.gisRef.ref)
                            )
                        }
                    }
                }
            }*/

            viewModel.state.collectLatest { state ->
                when {
                    state.trips.isNotEmpty() -> {
                        state.trips.forEach { gisRoute.add(it) }
                    }
                }
            }

            setupOnRecyclerView()
        }
    }

    private fun setupOnRecyclerView() {
        val legs = args.trip.legList?.leg
        val gis = gisRoute

        binding.rvJourneyDetails.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = TripsDetailsAdapter(
                legs = legs ?: listOf(),
                gis = gis,
                onClick = { onLegClick(it) }
            )
        }
    }

    private fun setupOnBackPressed() {
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            findNavController().popBackStack()
        }
    }

    private fun onLegClick(leg: Leg) {
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