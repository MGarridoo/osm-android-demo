package com.example.osmdemo.features.trips.domain

import com.example.osmdemo.core.backend.model.Trip
import com.example.osmdemo.core.backend.utils.BackendResult
import kotlinx.coroutines.flow.Flow

interface TripsRepository {

    fun retrieveTripSearchDoor2Door(
        originId: String? = null,
        originCoordLat: Double? = null,
        originCoordLong: Double? = null,
        originCoordName: String? = null,
        destId: String? = null,
        destCoordLat: Double? = null,
        destCoordLong: Double? = null,
        destCoordName: String? = null,
        time: String? = null,
        date: String? = null,
        groupFilter: String? = null,
        attributes: String? = null,
        products: Int? = null,
        totalWalk: String? = null,
        totalBike: String? = null,
        totalCar: String? = null,
        changeTimePercent: Int? = null,
        maxChange: Int? = null,
        minChangeTime: Int? = null,
        addChangeTime: Int? = null,
        maxChangeTime: Int? = null,
        rtMode: String? = null,
        poly: Int? = 1,
        passlist: Int? = 1,
        searchForArrival: Int? = 0,
        tariff: Int?,
        lang: String = "es",
        format: String = "json"
    ): Flow<BackendResult<List<Trip>>>

}