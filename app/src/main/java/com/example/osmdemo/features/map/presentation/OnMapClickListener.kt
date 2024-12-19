package com.example.osmdemo.features.map.presentation

import com.example.osmdemo.core.backend.model.Trip

interface OnMapClickListener {
    fun onTripDetailsClick(trip: Trip)
}