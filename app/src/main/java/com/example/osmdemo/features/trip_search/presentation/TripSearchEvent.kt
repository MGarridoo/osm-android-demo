package com.example.osmdemo.features.trip_search.presentation

sealed interface TripSearchEvent {
    data class OnTextChanged(val locationName: String) : TripSearchEvent
}