package com.example.osmdemo.features.map.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.osmdemo.core.backend.model.Trip
import com.example.osmdemo.core.backend.utils.BackendResult
import com.example.osmdemo.features.map.data.MapRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MapViewModel @Inject constructor(
    private val repo: MapRepositoryImpl
) : ViewModel() {

    var state = MutableStateFlow(MapState(isLoading = true))
        private set

    fun onEvent(event: MapEvent) {
        viewModelScope.launch {
            when(event) {
                is MapEvent.LoadMap -> { loadMap() }

                is MapEvent.RetrieveGisRoute -> { retrieveGisRoute(event.ctx) }

                is MapEvent.JourneyPos -> {
                    journeyPos(
                        llLon = event.llLon,
                        llLat = event.llLat,
                        urLon = event.urLon,
                        urLat = event.urLat,
                        jid = event.jid,
                        lines = event.lines,
                        date = event.date,
                        time = event.time,
                    )
                }
            }
        }
    }

    private fun loadMap() {
        viewModelScope.launch {

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

    private fun journeyPos(
        llLon: Double,
        llLat: Double,
        urLon: Double,
        urLat: Double,
        jid: String?,
        lines: String?,
        date: String?,
        time: String?,
    ) {
        Log.d("TAG", "journeyPos: llLon -- > $llLon")
        Log.d("TAG", "journeyPos: llLat -- > $llLat")
        Log.d("TAG", "journeyPos: urLon -- > $urLon")
        Log.d("TAG", "journeyPos: urLat -- > $urLat")
        Log.d("TAG", "journeyPos: jid -- > $jid")
        Log.d("TAG", "journeyPos: lines -- > $lines")

        viewModelScope.launch {
            repo.retrieveJourneyPos(
                llLat = llLat,
                llLon = llLon,
                urLat = urLat,
                urLon = urLon,
                jid = jid,
                lines = lines
            )
                .distinctUntilChanged()
                .collectLatest { result ->
                    when(result) {
                        is BackendResult.Error -> { onError(result.message, result.code) }
                        is BackendResult.Exception -> { onException(result.e) }
                        is BackendResult.Success -> {
                            val journeys = result.data
                            if (journeys.isNotEmpty()) {
                                Log.d("TAG", "journeyPos: size ${journeys[0]}")
                                Log.d("TAG", "journeyPos: size ${journeys.size}")
                                state.update { it.copy(journeys = journeys) }
                            } else {
                                Log.d("TAG", "journeyPos: Lista vacía")
                            }
                        }
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