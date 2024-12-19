package com.example.osmdemo.features.trip_search.presentation

import com.example.osmdemo.core.backend.model.StopOrCoordLocation

data class TripSearchState(
    val stopOrCoordLocation: List<StopOrCoordLocation> = listOf(),
    val startLocation: StopOrCoordLocation? = null,
    val destinationLocation: StopOrCoordLocation? = null,
    val isLoading: Boolean = false,
    val errorCode: Int? = null,
    val exception: Throwable? = null,
    val errorMessage: String? = null
)
