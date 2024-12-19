package com.example.osmdemo.core.backend.domain

import com.example.osmdemo.core.backend.model.Journey
import com.example.osmdemo.core.backend.model.JourneyPosResponse
import com.example.osmdemo.core.backend.model.StopOrCoordLocation
import com.example.osmdemo.core.backend.model.Trip
import com.example.osmdemo.core.backend.utils.BackendResult
import kotlinx.coroutines.flow.Flow

interface BackendRepository {

    /**
    * RoutePlannerLocation
    * */

    fun retrieveLocationName(
        input: String,
        withProducts: String? = null,
        type: String? = null,
        maxNo: String? = null,
    ): Flow<BackendResult<List<StopOrCoordLocation>>>

    fun retrieveLocationSearchInBoundingBox(
        llLon: Double,
        llLat: Double,
        urLon: Double,
        urLat: Double,
        positionMode: String,
        maxNo: String,
    ) : Flow<BackendResult<Unit>>

    /**
    * RoutePlannerTripSearch
    * */

    fun retrieveTripSearchDoor2Door(
        originId: String? = null,
        originCoordLong: String? = null,
        originCoordLat: String? = null,
        originCoordName: String? = null,
        destId: String? = null,
        destCoordLong: String? = null,
        destCoordLat: String? = null,
        destCoordName: String? = null,
        withFreq: String? = null,
        originWalk: String? = null,
        destWalk: String? = null,
        time: String? = null,
        poly: String? = null,
        polyEnc: String? = null,
        tripName: String? = null,
        tripAddress: String? = null,
        ivOnly: String? = null,
        totalCar: String? = null,
        totalWalk: String? = null,
        totalBike: String? = null,
        date: String? = null,
        searchForArrival: Int? = null,
        groupFilter: String? = null,
        attributes: String? = null,
        products: String? = null,
        passlist: Int? = null,
        changeTimePercent: Int? = null,
        maxChange: Int? = 0,
        maxChangeTime: Int? = null,
        minChangeTime: Int? = 0,
        rtMode: String? = null,
        addChangeTime: Int? = null,
        tariff: Int? = 0,
    ) : Flow<BackendResult<List<Trip>>>

    /**
     * RoutePlannerGis
     * */

    fun retrieveGisRoute(
        ctx: String,
        poly: String,
        polyEnc: String
    ): Flow<BackendResult<List<Trip>>>

    /**
     *
     * */
    fun retrieveJourneyPos(
        requestId: String?,
        requestFormat: String?,
        jsonCallback: String?,
        llLat: Double,
        llLon: Double,
        urLat: Double,
        urLon: Double,
        operators: String?,
        attributes: String?,
        jid: String?,
        lines: String?,
        infoTexts: String?,
        maxJny: Int?,
        periodSize: String?,
        periodStep: String?,
        time: String?,
        date: String?,
        positionMode: String?,
    ): Flow<BackendResult<List<Journey>>>

    fun retrieveJourneyDetail() : Flow<BackendResult<Unit>>
}