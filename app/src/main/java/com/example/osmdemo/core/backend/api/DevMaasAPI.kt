package com.example.osmdemo.core.backend.api

import com.example.osmdemo.core.backend.model.JourneyPosResponse
import com.example.osmdemo.core.backend.model.LocationResponse
import com.example.osmdemo.core.backend.model.TripResponse
import com.example.osmdemo.core.backend.utils.BackendResult
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query

interface DevMaasAPI {

    @POST("api/RoutePlannerLocation/RetrieveLocationByName")
    suspend fun retrieveLocationByName(
        @Header("Firmware") firmware: String = "",
        @Body params: Map<String, String>
    ): BackendResult<LocationResponse>

    @POST("api/RoutePlannerTripSearch/RetrieveTripSearchDoor2Door")
    suspend fun retrieveTripSearchDoor2Door(
        @Header("Firmware")  firmware: String = "",
        @Body params: Map<String, String>,
    ): BackendResult<TripResponse>

    @GET("/api/RoutePlannerLocation/RetrieveLocationSearchInBoundingBox")
    suspend fun retrieveLocationSearchInBoundingBox(
        llLon: Double,
        llLat: Double,
        urLon: Double,
        urLat: Double,
        positionMode: String,
        maxNo: String,
    ): BackendResult<Unit>

    @POST("api/RoutePlannerGis/RetrieveGisRoute")
    suspend fun retrieveGisRoute(
        @Header("Firmware")  firmware: String = "",
        @Body params: Map<String, String>,
    ): BackendResult<TripResponse>

    @POST("api/RoutePlannerGis/RetrieveJourneyPos")
    suspend fun retrieveJourneyPos(
        @Header("Firmware")  firmware: String = "",
        @Body params: Map<String, String>,
    ): BackendResult<JourneyPosResponse>

}