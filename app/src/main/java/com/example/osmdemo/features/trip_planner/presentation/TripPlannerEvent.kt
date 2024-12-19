package com.example.osmdemo.features.trip_planner.presentation

import com.example.osmdemo.core.backend.model.StopOrCoordLocation
import java.util.Calendar

enum class LOCATION { A, B }

sealed interface TripPlannerEvent {
    data class OnLocationSelected(val location: StopOrCoordLocation, val locationType: LOCATION) : TripPlannerEvent
    data class OnDateTimeSelected(val selectedDateTime: Calendar, val isDeparture: Boolean) : TripPlannerEvent
}