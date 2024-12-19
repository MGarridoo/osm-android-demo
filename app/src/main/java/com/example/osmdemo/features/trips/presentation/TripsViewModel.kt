package com.example.osmdemo.features.trips.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.osmdemo.core.backend.model.Trip
import com.example.osmdemo.core.backend.utils.BackendResult
import com.example.osmdemo.features.trips.data.TripsRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TripsViewModel @Inject constructor(
    private val repo: TripsRepositoryImpl,
): ViewModel() {

    var state = MutableStateFlow(TripsState())
        private set

    fun onEvent(event: TripsEvent) {
        viewModelScope.launch {
            when(event) {
                is TripsEvent.OnSearchTrip -> { onSearchTrip(event) }
            }
        }
    }

    private fun onSearchTrip(event: TripsEvent.OnSearchTrip) {
        viewModelScope.launch {
            state.update { it.copy(isLoading = true) }
            repo.retrieveTripSearchDoor2Door(
                originId = event.originId,
                originCoordLat = event.originCoordLat,
                originCoordLong = event.originCoordLong,
                originCoordName = event.originCoordName,
                destId = event.destId,
                destCoordLat = event.destCoordLat,
                destCoordLong = event.destCoordLong,
                destCoordName = event.destCoordName,
                time = event.time,
                date = event.date,
                groupFilter = event.groupFilter,
                attributes = event.attributes,
                products = event.products,
                totalWalk = event.totalWalk,
                totalBike = event.totalBike,
                totalCar = event.totalCar,
                changeTimePercent = event.changeTimePercent,
                maxChange = event.maxChange,
                minChangeTime = event.minChangeTime,
                addChangeTime = event.addChangeTime,
                maxChangeTime = event.maxChangeTime,
                rtMode = event.rtMode,
                poly = event.poly,
                passlist = event.passlist,
                searchForArrival = event.searchForArrival,
                tariff = 1
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

    private fun onError(message: String?, code: Int? = 0) {
        viewModelScope.launch {
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
            state.update {
                it.copy(
                    isLoading = false,
                    exception = e,
                )
            }
        }
    }

    private fun onSuccess(result: List<Trip>) {
        viewModelScope.launch {
            state.update {
                it.copy(
                    isLoading = false,
                    trips = result
                )
            }
        }
    }

}