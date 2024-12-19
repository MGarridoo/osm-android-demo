package com.example.osmdemo.features.trip_search.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.coroutineScope
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.osmdemo.databinding.FragmentTripSearchBinding
import com.example.osmdemo.features.trip_search.presentation.adapters.TripSearchAdapter
import com.example.osmdemo.utils.debounce
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.util.Calendar
import java.util.TimeZone

@AndroidEntryPoint
class TripSearchFragment : Fragment() {

    private var _binding: FragmentTripSearchBinding? = null
    private val binding get() = _binding!!

    private val args: TripSearchFragmentArgs by navArgs()
    private var isDeparture = true

    private val viewModel by viewModels<TripSearchViewModel>()

    private val debouncedSearch = debounce<String>(waitMs = 500L, scope = lifecycle.coroutineScope) { query ->
        viewModel.onEvent(TripSearchEvent.OnTextChanged(locationName = query))
    }

    private val calendar = Calendar.getInstance(TimeZone.getTimeZone("America/Bogota"))

    private val tripSearchAdapter by lazy {
        TripSearchAdapter { location ->
            val isStartInput = args.isStartInput

            val resultBundle = Bundle().apply {
                putParcelable("selectedLocation", location)
                putBoolean("isStartInput", isStartInput)
            }

            parentFragmentManager.setFragmentResult("tripSearchResult", resultBundle)
            findNavController().popBackStack()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTripSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let { args ->
            isDeparture = args.getBoolean("isDeparture", true)
            val timestamp = args.getLong("timestamp", System.currentTimeMillis())
            calendar.timeInMillis = timestamp
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //val userAgent = "[${BuildConfig.APPLICATION_ID}]/[${BuildConfig.VERSION_CODE}]"
        //val coderNominatim = GeocoderNominatim(Locale.forLanguageTag("CO"), userAgent)

        /*lifecycleScope.launch(IO) {
            try {
                val results = coderNominatim.getFromLocationName("La Parrilla moderna", 5)

                Log.d("TAG", "onViewCreated: $results")
                
                *//*if (results.isNotEmpty()) {
                    val firstResult = results[0]
                    val geoPoint = GeoPoint(firstResult.latitude, firstResult.longitude)


                    Log.d("TAG", "onViewCreated: $geoPoint")

                }*//*
            } catch (e: Exception) {
                Log.e("TAG", "onViewCreated: catch ${e.message}")
            }
        }*/


        setupRecyclerView()
        onTextListener()
        onStateListener()
        onNavigationOnClick()
        onBackPressed()
        onTextChanged()
    }

    /**
     * Click de botón de navegación
     * */
    private fun onNavigationOnClick() {
        binding.toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
    }

    /**
     *  Establece el texto actual como valor inicial del campo de búsqueda
     * */
    private fun onTextListener() {
        args.currentText?.let { text ->
            if (text.isNotBlank()) {
                binding.searchInput.setText(text)
                binding.searchInput.setSelection(text.length)
                debouncedSearch(text) // Se hace la consulta al servidor
            }
        }
    }

    private fun onStateListener() {
        lifecycleScope.launch {
            viewModel.state.collectLatest { result ->
                when {
                    result.isLoading -> { binding.progressBar.visibility = View.VISIBLE }
                    result.stopOrCoordLocation.isNotEmpty() -> {
                        tripSearchAdapter.submitList(result.stopOrCoordLocation)
                        binding.progressBar.visibility = View.GONE
                    }
                    else -> {
                        binding.progressBar.visibility = View.GONE
                    }
                }
            }
        }
    }

    private fun onTextChanged() {
        binding.searchInput.doOnTextChanged { text, _, _, _ ->
            if (text?.isNotEmpty() == true) {
                debouncedSearch("$text")
            }
        }
    }

    private fun onBackPressed() {
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            findNavController().popBackStack()
        }
    }

    private fun setupRecyclerView() {
        binding.rvSearch.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = tripSearchAdapter
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}