package com.example.osmdemo.features.map.data

import com.example.osmdemo.core.backend.utils.BackendResult
import com.example.osmdemo.core.backend.domain.BackendRepository
import com.example.osmdemo.core.backend.model.Journey
import com.example.osmdemo.core.backend.model.JourneyPosResponse
import com.example.osmdemo.core.backend.model.Trip
import com.example.osmdemo.features.map.domain.MapRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MapRepositoryImpl @Inject constructor(
    private val repo: BackendRepository,
) : MapRepository {

   /* override suspend fun getDataInfo(
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
    ): BackendResult<LocationResponse> {
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
    ): BackendResult<LocationResponse> {
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
    ): BackendResult<ResponseBody> {
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
    ): BackendResult<DepartureResponse> {
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
        originId: String?,
        originCoordLat: Double?,
        originCoordLong: Double?,
        originCoordName: String?,
        destId: String?,
        destCoordLat: Double?,
        destCoordLong: Double?,
        destCoordName: String?,
        groupFilter: String?,
        attributes: String?,
        products: Int?,
        totalWalk: String?,
        totalBike: String?,
        totalCar: String?,
        changeTimePercent: Int?,
        maxChange: Int?,
        minChangeTime: Int?,
        addChangeTime: Int?,
        maxChangeTime: Int?,
        rtMode: String?,
        poly: Int?,
        passlist: Int?,
        accessId: String,
        lang: String,
        format: String,
    ): BackendResult<TripResponse> {
        return api.searchTrip(
            originId = originId,
            originCoordLat = originCoordLat,
            originCoordLong = originCoordLong,
            originCoordName = originCoordName,
            destId = destId,
            destCoordLat = destCoordLat,
            destCoordLong = destCoordLong,
            destCoordName = destCoordName,
            groupFilter = groupFilter,
            attributes = attributes,
            products = products,
            totalWalk = totalWalk,
            totalBike = totalBike,
            totalCar = totalCar,
            changeTimePercent = changeTimePercent,
            maxChangeTime = maxChangeTime,
            addChangeTime = addChangeTime,
            maxChange = maxChange,
            minChangeTime = minChangeTime,
            rtMode = rtMode,
            poly = poly,
            passlist = passlist,
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
    ): BackendResult<LocationResponse> {
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
    }*/

    override suspend fun retrieveGisRoute(
        ctx: String,
        poly: Int?,
        polyEnc: String?
    ): Flow<BackendResult<List<Trip>>> {
        return repo.retrieveGisRoute(ctx = ctx, poly = "$poly", polyEnc = "$polyEnc")
    }

    override suspend fun retrieveJourneyPos(
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
        positionMode: String?
    ): Flow<BackendResult<List<Journey>>> {
        return repo.retrieveJourneyPos(
            requestId = requestId,
            requestFormat = requestFormat,
            jsonCallback = jsonCallback,
            llLat = llLat,
            llLon = llLon,
            urLat = urLat,
            urLon = urLon,
            operators = operators,
            attributes = attributes,
            jid = jid,
            lines = lines,
            infoTexts = infoTexts,
            maxJny = maxJny,
            periodSize = periodSize,
            periodStep = periodStep,
            time = time,
            date = date,
            positionMode = positionMode
        )
    }

}
