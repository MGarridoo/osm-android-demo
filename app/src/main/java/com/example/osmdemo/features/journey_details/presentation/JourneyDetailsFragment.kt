package com.example.osmdemo.features.journey_details.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.osmdemo.core.backend.utils.BackendResult
import com.example.osmdemo.databinding.FragmentJourneyDetailsBinding
import com.example.osmdemo.core.backend.model.Stop
import com.example.osmdemo.features.map.domain.MapRepository
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class JourneyDetailsFragment : Fragment() {

    private var _binding: FragmentJourneyDetailsBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var map: MapRepository

    private val args: JourneyDetailsFragmentArgs by navArgs()

    private val accessId = "6aa3add1-6376-403f-93e8-1a4c61383d4a" // Token del API

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentJourneyDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupOnJourneyDetails()
        setupOnBackPressed()
    }

    private fun setupOnJourneyDetails() {
        lifecycleScope.launch {
            val ref = args.ref
            /*val result = map.getJourneyDetails(
                journeyId = ref,
                poly = 1,
                polyEnc = "N",
                accessId = accessId,
            )

            when(result) {
                is BackendResult.Error -> { }
                is BackendResult.Exception -> { }
                is BackendResult.Success -> { setupOnRecyclerView(result.data.stops.stop) }
            }*/
        }
    }

    private fun setupOnRecyclerView(stops: List<Stop>) {
        binding.rvJourneyDetails.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = JourneyDetailsAdapter(stops)
        }
    }

    private fun setupOnBackPressed() {
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            findNavController().popBackStack()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}