package com.example.osmdemo.features.map.presentation

import com.example.osmdemo.core.backend.model.Journey
import com.example.osmdemo.core.backend.model.Trip

data class MapState(
    val isLoading: Boolean = false,
    val trips: List<Trip> = listOf(),
    val journeys: List<Journey> = listOf(),
    val errorCode: Int? = null,
    val exception: Throwable? = null,
    val errorMessage: String? = null
)
