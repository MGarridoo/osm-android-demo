package com.example.osmdemo.trips_details.presentation

import com.example.osmdemo.map.data.model.Trip

interface OnTripClickListener {
    fun onTripClick(trip: Trip)
}