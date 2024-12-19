package com.example.osmdemo.features.trips.presentation

import com.example.osmdemo.core.backend.model.Leg

interface OnLegClickListener {
    fun onLegClick(leg: Leg)
}