package com.example.osmdemo.features.trip_planner.domain

import com.example.osmdemo.core.backend.model.DepartureResponse
import com.example.osmdemo.core.backend.model.Journey
import com.example.osmdemo.core.backend.model.LocationResponse
import com.example.osmdemo.core.backend.model.StopResponse
import com.example.osmdemo.core.backend.utils.BackendResult
import kotlinx.coroutines.flow.Flow

interface TripPlannerRepository {

    fun locationName(location: String): Flow<BackendResult<LocationResponse>>

    fun locationBoundingBox(
        llLon: Double,
        llLat: Double,
        urLon: Double,
        urLat: Double,
        products: Int = 256,
        type: String = "",
    ) : Flow<BackendResult<LocationResponse>>

    fun departureBoard(
        id: String,
        date: String? = null,
        time: String? = null,
        maxJourneys: Int? = null,
        products: Int = 256,
        passlist: Int = 0,
    ) : Flow<BackendResult<DepartureResponse>>

    fun retrieveJourneyPos(
        requestId: String? = null,
        requestFormat: String? = null,
        jsonCallback: String? = null,
        llLat: Double,
        llLon: Double,
        urLat: Double,
        urLon: Double,
        operators: String? = null,
        attributes: String? = null,
        jid: String? = null,
        lines: String? = null,
        infoTexts: String? = null,
        maxJny: Int? = null,
        periodSize: String? = null,
        periodStep: String? = null,
        time: String? = null,
        date: String? = null,
        positionMode: String? = null,
    ): Flow<BackendResult<List<Journey>>>

    fun getJourneyDetails(
        journeyId: String,
        poly: Int?,
        polyEnc: String? = null,
    ): Flow<BackendResult<StopResponse>>

}