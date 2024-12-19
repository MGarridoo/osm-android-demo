package com.example.osmdemo.features.trips_details.presentation

import com.example.osmdemo.core.backend.model.Trip

interface OnTripClickListener {
    fun onTripClick(trip: Trip)
}