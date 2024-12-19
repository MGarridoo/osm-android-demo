package com.example.osmdemo.features.trips.presentation

sealed interface TripsEvent {
    data class OnSearchTrip(
        val originId: String? = null,
        val originCoordLat: Double? = null,
        val originCoordLong: Double? = null,
        val originCoordName: String? = null,
        val destId: String? = null,
        val destCoordLat: Double? = null,
        val destCoordLong: Double? = null,
        val destCoordName: String? = null,
        val time: String? = null,
        val date: String? = null,
        val groupFilter: String? = null,
        val attributes: String? = null,
        val products: Int? = null,
        val totalWalk: String? = null,
        val totalBike: String? = null,
        val totalCar: String? = null,
        val changeTimePercent: Int? = null,
        val maxChange: Int? = null,
        val minChangeTime: Int? = null,
        val addChangeTime: Int? = null,
        val maxChangeTime: Int? = null,
        val rtMode: String? = null,
        val poly: Int? = 1,
        val passlist: Int? = 1,
        val searchForArrival: Int = 0,
        val lang: String = "es",
        val format: String = "json"
    ) : TripsEvent
}
