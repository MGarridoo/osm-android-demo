package com.example.osmdemo.features.trip_planner.presentation

import com.example.osmdemo.core.backend.model.Journey
import com.example.osmdemo.core.backend.model.PolylineGroup
import com.example.osmdemo.core.backend.model.StopOrCoordLocation
import com.example.osmdemo.shared.extensions.DateTimeUtils.formatDateTime
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import java.util.TimeZone

data class TripPlannerState(
    val startLocation: StopOrCoordLocation? = null,
    val destinationLocation: StopOrCoordLocation? = null,
    val journeys: List<Journey> = listOf(),
    val polylineGroups: List<PolylineGroup?> = listOf(),
    val selectedDateTime: Calendar? = null,
    val isDeparture: Boolean = true,
    val isFirstTime: Boolean = true,
    val isLoading: Boolean = false,
    val errorCode: Int? = null,
    val exception: Throwable? = null,
    val errorMessage: String? = null
) {
    private val bogotaTimeZone: TimeZone = TimeZone.getTimeZone("America/Bogota")

    val displayTime: String
        get() {
            return if (isFirstTime) {
                "Ahora"
            } else {
                selectedDateTime?.formatDateTime(isDeparture) ?: "Ahora"
            }
        }

    val date: String?
        get() {
            val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            dateFormat.timeZone = bogotaTimeZone
            return selectedDateTime?.let { dateFormat.format(it.time) }
        }

    val time: String?
        get() {
            val timeFormat = SimpleDateFormat("HH:mm:ss", Locale.getDefault())
            timeFormat.timeZone = bogotaTimeZone
            return selectedDateTime?.let { timeFormat.format(it.time) }
        }
}
