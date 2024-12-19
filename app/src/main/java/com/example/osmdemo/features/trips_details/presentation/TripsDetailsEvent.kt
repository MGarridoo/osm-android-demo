package com.example.osmdemo.features.trips_details.presentation

sealed interface TripsDetailsEvent {
    data class RetrieveGisRoute(val ctx: String) : TripsDetailsEvent
}