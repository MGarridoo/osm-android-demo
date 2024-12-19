package com.example.osmdemo.features.trips_details.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.osmdemo.core.backend.model.Trip
import com.example.osmdemo.core.backend.utils.BackendResult
import com.example.osmdemo.features.trips_details.data.TripsDetailsRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TripsDetailsViewModel @Inject constructor(
    private val repo: TripsDetailsRepositoryImpl
): ViewModel() {

    var state = MutableStateFlow(TripsDetailsState())
        private set


    fun onEvent(event: TripsDetailsEvent) {
        viewModelScope.launch {
            when(event) {
                is TripsDetailsEvent.RetrieveGisRoute -> { retrieveGisRoute(event.ctx) }
            }
        }
    }

    private fun retrieveGisRoute(ctx: String) {
        viewModelScope.launch {
            repo.retrieveGisRoute(
                ctx = ctx,
                poly = 1,
                polyEnc = "N"
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

    private fun onSuccess(trips: List<Trip>) {
        viewModelScope.launch {
            state.update {
                it.copy(
                    isLoading = false,
                    trips = trips
                )
            }
        }
    }

}