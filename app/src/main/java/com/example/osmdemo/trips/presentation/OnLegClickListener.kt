package com.example.osmdemo.trips.presentation

import com.example.osmdemo.map.data.model.Leg

interface OnLegClickListener {
    fun onLegClick(leg: Leg)
}