package com.example.osmdemo.features.trips_details.presentation

import com.example.osmdemo.core.backend.model.Trip

data class TripsDetailsState(
    val trips : List<Trip> = listOf(),
    val isLoading: Boolean = false,
    val errorCode: Int? = null,
    val exception: Throwable? = null,
    val errorMessage: String? = null
)
