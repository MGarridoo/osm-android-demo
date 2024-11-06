package com.example.osmdemo.map.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MapViewModel @Inject constructor() : ViewModel() {

    var state = MutableStateFlow(MapState(isLoading = true))
        private set

    /*fun onEvent(event: MapEvent) {
        viewModelScope.launch {
            when(event) {
                else -> { }
            }
        }
    }*/


}