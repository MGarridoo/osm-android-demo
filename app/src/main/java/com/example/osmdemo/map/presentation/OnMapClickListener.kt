package com.example.osmdemo.map.presentation

import com.example.osmdemo.map.data.model.Trip

interface OnMapClickListener {
    fun onTripDetailsClick(trip: Trip)
}