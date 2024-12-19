package com.example.osmdemo.features.trip_planner.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.osmdemo.core.backend.model.Journey
import com.example.osmdemo.core.backend.utils.BackendResult
import com.example.osmdemo.features.trip_planner.data.TripPlannerRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Calendar
import javax.inject.Inject

@HiltViewModel
class TripPlannerViewModel @Inject constructor(
    private val repo: TripPlannerRepositoryImpl,
) : ViewModel() {

    var state = MutableStateFlow(TripPlannerState())
        private set

    fun onEvent(event: TripPlannerEvent) {
        viewModelScope.launch {
            when(event) {

                is TripPlannerEvent.OnDateTimeSelected -> {
                    // Actualiza la fecha, hora y tipo de viaje
                    state.update {
                        it.copy(
                            selectedDateTime = event.selectedDateTime,
                            isDeparture = event.isDeparture
                        )
                    }
                }

                is TripPlannerEvent.OnLocationSelected -> {
                    if (event.locationType == LOCATION.A) {
                        state.update { it.copy(startLocation = event.location) }
                    } else {
                        state.update { it.copy(destinationLocation = event.location) }
                    }
                }

            }
        }
    }

    fun onJourneyPos(
        llLon: Double,
        llLat: Double,
        urLon: Double,
        urLat: Double,
        jid: String?,
        date: String?,
        time: String?,
    ) {
        viewModelScope.launch {
            state.update {
                it.copy(
                    polylineGroups = emptyList(),
                    journeys = emptyList(),
                    isLoading = true
                )
            }
            Log.d("TAG", "onTest: polylineGroups después de limpiar -> ${state.value.polylineGroups}")
            repo.retrieveJourneyPos(
                llLat = llLat,
                llLon = llLon,
                urLat = urLat,
                urLon = urLon,
                maxJny = 10,
            )
                .distinctUntilChanged()
                .collectLatest { result ->
                    when(result) {
                        is BackendResult.Error -> { onError(result.message, result.code) }
                        is BackendResult.Exception -> { onException(result.e) }
                        is BackendResult.Success -> { onSuccess(result.data) }
                    }
                }
        }
    }

    fun updateDateTime(selectedDateTime: Calendar, isDeparture: Boolean) {
        state.update {
            it.copy(
                selectedDateTime = selectedDateTime,
                isDeparture = isDeparture,
                isFirstTime = false
            )
        }
    }

    private fun onError(message: String?, code: Int? = 0) {
        viewModelScope.launch {
            Log.d("TAG", "onError: $message")
            state.update {
                it.copy(
                    isLoading = false,
                    errorMessage = message ?: "Hubo un error",
                )
            }
        }
    }

    private fun onException(e: Throwable) {
        viewModelScope.launch {
            Log.d("TAG", "onException: $e")
            state.update {
                it.copy(
                    isLoading = false,
                    exception = e,
                )
            }
        }
    }

    private fun onSuccess(journeys: List<Journey>) {
        if (journeys.isNotEmpty()) {
            state.update {
                it.copy(
                    journeys = journeys,
                    isLoading = false
                )
            }
        } else {
            state.update {
                it.copy(
                    isLoading = false
                )
            }
        }
    }

}