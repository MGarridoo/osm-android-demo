package com.example.osmdemo.map.data.repository

import com.example.osmdemo.map.data.model.TripResponse
import com.example.osmdemo.map.data.model.Locations
import com.example.osmdemo.core.backend.BackendResult
import com.example.osmdemo.map.data.api.API
import com.example.osmdemo.map.data.model.NearbyStopsResponse
import com.example.osmdemo.map.data.model.StopResponse
import com.example.osmdemo.map.domain.repository.MapRepository
import okhttp3.ResponseBody
import javax.inject.Inject

class MapRepositoryImpl @Inject constructor(
    private val api: API,
) : MapRepository {

    override suspend fun getDataInfo(
        accessId: String,
        format: String
    ): BackendResult<ResponseBody> {
        return api.getDataInfo(
            accessId = accessId,
            format = format
        )
    }

    override suspend fun getDataInfoWithBearer(
        bearerToken: String,
        accept: String
    ): BackendResult<ResponseBody> {
        return api.getDataInfoWithBearer(
            bearerToken = bearerToken,
            accept = accept
        )
    }

    override suspend fun getTimetableInfo(
        accessId: String,
        format: String
    ): BackendResult<ResponseBody> {
        return api.getTimetableInfo(
            accessId = accessId,
            format = format
        )
    }

    override suspend fun searchLocationByName(
        input: String,
        type: String?,
        lang: String,
        withProducts: Int,
        accessId: String,
        format: String
    ): BackendResult<Locations> {
        return api.searchLocationByName(
            input = input,
            type = type,
            lang = lang,
            withProducts = withProducts,
            accessId = accessId,
            format = format
        )
    }

    override suspend fun searchLocationByNameWithProduct(
        input: String,
        products: Int,
        lang: String,
        accessId: String
    ): BackendResult<ResponseBody> {
        return api.searchLocationByNameWithProduct(
            input = input,
            products = products,
            lang = lang,
            accessId = accessId
        )
    }

    override suspend fun searchLocationInBoundingBox(
        llLon: Double,
        llLat: Double,
        urLon: Double,
        urLat: Double,
        type: String?,
        products: Int?,
        accessId: String,
        format: String?
    ): BackendResult<Locations> {
        return api.searchLocationInBoundingBox(
            llLon = llLon,
            llLat = llLat,
            urLon = urLon,
            urLat = urLat,
            type = type,
            products = products,
            accessId = accessId,
            format = format
        )
    }

    override suspend fun searchNearbyStops(
        originCoordLong: Double,
        originCoordLat: Double,
        accessId: String,
        format: String
    ): BackendResult<NearbyStopsResponse> {
        return api.searchNearbyStops(
            originCoordLong = originCoordLong,
            originCoordLat = originCoordLat,
            accessId = accessId,
            format = format
        )
    }

    override suspend fun getDepartureBoard(
        time: String,
        products: Int,
        maxJourneys: Int,
        passlist: Int,
        accessId: String,
        lang: String,
        id: String
    ): BackendResult<ResponseBody> {
        return api.getDepartureBoard(
            time = time,
            products = products,
            maxJourneys = maxJourneys,
            passlist = passlist,
            accessId = accessId,
            lang = lang,
            id = id
        )
    }

    override suspend fun getArrivalBoard(
        id: String,
        accessId: String
    ): BackendResult<ResponseBody> {
        return api.getArrivalBoard(
            id = id,
            accessId = accessId
        )
    }

    override suspend fun getJourneyDetails(
        journeyId: String,
        poly: Int?,
        polyEnc: String?,
        accessId: String
    ): BackendResult<StopResponse> {
        return api.getJourneyDetails(
            journeyId = journeyId,
            poly = poly,
            polyEnc = polyEnc,
            accessId = accessId
        )
    }

    override suspend fun searchTrip(
        originId: String,
        destId: String,
        time: String?,
        maxChange: Int?,
        withFreq: Int?,
        poly: Int?,
        polyEnc: String?,
        accessId: String,
        lang: String,
        format: String
    ): BackendResult<TripResponse> {
        return api.searchTrip(
            originId = originId,
            destId = destId,
            time = time,  // Opcional
            maxChange = maxChange,  // Opcional
            withFreq = withFreq,  // Opcional
            poly = poly,
            polyEnc = polyEnc,
            accessId = accessId,
            lang = lang,
            format = format
        )
    }

    override suspend fun searchTripDoorToDoor(
        originCoordLong: Double,
        originCoordLat: Double,
        destCoordLong: Double,
        destCoordLat: Double,
        originWalk: String,
        destWalk: String,
        time: String,
        accessId: String,
        lang: String,
        format: String
    ): BackendResult<Locations> {
        return api.searchTripDoorToDoor(
            originCoordLong = originCoordLong,
            originCoordLat = originCoordLat,
            destCoordLong = destCoordLong,
            destCoordLat = destCoordLat,
            originWalk = originWalk,
            destWalk = destWalk,
            time = time,
            accessId = accessId,
            lang = lang,
            format = format
        )
    }

    override suspend fun getGISRoute(
        ctx: String,
        poly: Int?,
        polyEnc: String?,
        accessId: String,
        lang: String,
        format: String,
    ): BackendResult<TripResponse> {
        return api.getGISRoute(
            ctx = ctx,
            poly = poly,
            polyEnc = polyEnc,
            lang = lang,
            accessId = accessId
        )
    }
}
