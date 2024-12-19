package com.example.osmdemo.features.map.domain

import com.example.osmdemo.core.backend.model.Journey
import com.example.osmdemo.core.backend.model.JourneyPosResponse
import com.example.osmdemo.core.backend.model.Trip
import com.example.osmdemo.core.backend.model.TripResponse
import com.example.osmdemo.core.backend.utils.BackendResult
import kotlinx.coroutines.flow.Flow
import retrofit2.http.Query

interface MapRepository {

    /*suspend fun getDataInfo(
        accessId: String,
        format: String = "json"
    ): BackendResult<ResponseBody>

    suspend fun getDataInfoWithBearer(
        bearerToken: String,
        accept: String = "application/json"
    ): BackendResult<ResponseBody>

    suspend fun getTimetableInfo(
        accessId: String,
        format: String = "json"
    ): BackendResult<ResponseBody>

    suspend fun searchLocationByName(
        input: String,
        type: String? = null,
        lang: String = "es",
        withProducts: Int = 256,
        accessId: String,
        format: String = "json"
    ): BackendResult<LocationResponse>

    suspend fun searchLocationByNameWithProduct(
        input: String,
        products: Int,
        lang: String,
        accessId: String
    ): BackendResult<ResponseBody>

    suspend fun searchLocationInBoundingBox(
        llLon: Double,
        llLat: Double,
        urLon: Double,
        urLat: Double,
        type: String? = null,  // Optional
        products: Int? = null,  // Optional
        accessId: String,
        format: String? = "json"  // Optional
    ): BackendResult<LocationResponse>

    suspend fun searchNearbyStops(
        originCoordLong: Double,
        originCoordLat: Double,
        accessId: String,
        format: String = "json"
    ): BackendResult<ResponseBody>

    suspend fun getDepartureBoard(
        time: String,
        products: Int,
        maxJourneys: Int,
        passlist: Int = 0,  // Optional with default value
        accessId: String,
        lang: String = "es",  // Optional with default value
        id: String
    ): BackendResult<DepartureResponse>

    suspend fun getArrivalBoard(
        id: String,
        accessId: String
    ): BackendResult<ResponseBody>

    suspend fun getJourneyDetails(
        journeyId: String,
        poly: Int? = null,
        polyEnc: String? = null,
        accessId: String
    ): BackendResult<StopResponse>

    suspend fun searchTrip(
        originId: String? = null,
        originCoordLat: Double? = null,
        originCoordLong: Double? = null,
        originCoordName: String? = null,
        destId: String? = null,
        destCoordLat: Double? = null,
        destCoordLong: Double? = null,
        destCoordName: String? = null,
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
        accessId: String,
        lang: String = "es",
        format: String = "json"
    ): BackendResult<TripResponse>

    suspend fun searchTripDoorToDoor(
        originCoordLong: Double,
        originCoordLat: Double,
        destCoordLong: Double,
        destCoordLat: Double,
        originWalk: String,
        destWalk: String,
        time: String,
        accessId: String,
        lang: String = "es",
        format: String = "json"
    ): BackendResult<LocationResponse>*/

    suspend fun retrieveGisRoute(
        ctx: String,
        poly: Int? = null,
        polyEnc: String? = null
    ): Flow<BackendResult<List<Trip>>>

    suspend fun retrieveJourneyPos(
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


}
