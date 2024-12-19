package com.example.osmdemo.features.trip_search.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.osmdemo.core.backend.model.StopOrCoordLocation
import com.example.osmdemo.core.backend.utils.BackendResult
import com.example.osmdemo.features.trip_search.data.TripSearchRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TripSearchViewModel @Inject constructor(
    private val repo: TripSearchRepositoryImpl,
) : ViewModel() {

    var state = MutableStateFlow(TripSearchState())
        private set

    fun onEvent(event: TripSearchEvent) {
        viewModelScope.launch {
            when(event) {
                is TripSearchEvent.OnTextChanged -> { onSearch(event.locationName) }
            }
        }
    }

    private fun onSearch(locationName: String) {
        viewModelScope.launch {
            state.update { it.copy(isLoading = true) }
            repo.locationName(location = locationName)
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

    private fun onSuccess(location: List<StopOrCoordLocation>) {
        viewModelScope.launch {
            state.update {
                it.copy(
                    isLoading = false,
                    stopOrCoordLocation = location
                )
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

}