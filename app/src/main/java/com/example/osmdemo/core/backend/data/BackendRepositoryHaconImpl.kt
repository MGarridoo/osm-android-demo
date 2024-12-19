package com.example.osmdemo.core.backend.data

import com.example.osmdemo.BuildConfig
import com.example.osmdemo.core.backend.api.HafasHaconAPI
import com.example.osmdemo.core.backend.domain.BackendRepository
import com.example.osmdemo.core.backend.model.Journey
import com.example.osmdemo.core.backend.model.JourneyPosResponse
import com.example.osmdemo.core.backend.model.StopOrCoordLocation
import com.example.osmdemo.core.backend.model.Trip
import com.example.osmdemo.core.backend.utils.BackendResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class BackendRepositoryHaconImpl @Inject constructor(
    private val api: HafasHaconAPI
): BackendRepository {

    override fun retrieveLocationName(
        input: String,
        withProducts: String?,
        type: String?,
        maxNo: String?
    ): Flow<BackendResult<List<StopOrCoordLocation>>> {
        return flow {
            val result = api.searchLocationByName(
                input = input,
                type = type,
                lang = "es",
                withProducts = withProducts?.toInt() ?: 0,
                accessId = BuildConfig.API_KEY,
                format = "json"
            )
            when(result) {
                is BackendResult.Error -> { emit(BackendResult.Error(result.code, result.message)) }
                is BackendResult.Exception -> { emit(BackendResult.Exception(result.e)) }
                is BackendResult.Success -> { emit(BackendResult.Success(result.data.locations)) }
            }
        }.catch { e ->
            emit(BackendResult.Exception(e))
        }
    }

    override fun retrieveTripSearchDoor2Door(
        originId: String?,
        originCoordLong: String?,
        originCoordLat: String?,
        originCoordName: String?,
        destId: String?,
        destCoordLong: String?,
        destCoordLat: String?,
        destCoordName: String?,
        withFreq: String?,
        originWalk: String?,
        destWalk: String?,
        time: String?,
        poly: String?,
        polyEnc: String?,
        tripName: String?,
        tripAddress: String?,
        ivOnly: String?,
        totalCar: String?,
        totalWalk: String?,
        totalBike: String?,
        date: String?,
        searchForArrival: Int?,
        groupFilter: String?,
        attributes: String?,
        products: String?,
        passlist: Int?,
        changeTimePercent: Int?,
        maxChange: Int?,
        maxChangeTime: Int?,
        minChangeTime: Int?,
        rtMode: String?,
        addChangeTime: Int?,
        tariff: Int?
    ): Flow<BackendResult<List<Trip>>> {
        return flow {
            val result = api.searchTrip(
                originId = originId,
                originCoordLat = originCoordLat?.toDouble(),
                originCoordLong = originCoordLong?.toDouble(),
                originCoordName = originCoordName,
                destId = destId,
                destCoordLat = destCoordLat?.toDouble(),
                destCoordLong = destCoordLong?.toDouble(),
                destCoordName = destCoordName,
                time = time,
                date = date,
                groupFilter = groupFilter,
                attributes = attributes,
                products = products?.toInt(),
                totalWalk = totalWalk,
                totalBike = totalBike,
                totalCar = totalCar,
                changeTimePercent = changeTimePercent,
                maxChange = maxChange,
                minChangeTime = minChangeTime,
                addChangeTime = addChangeTime,
                maxChangeTime = maxChangeTime,
                rtMode = rtMode,
                poly = poly?.toInt(),
                passlist = passlist,
                accessId = BuildConfig.API_KEY,
                searchForArrival = searchForArrival,
                lang = "es",
                format = "json"
            )
            when(result) {
                is BackendResult.Error -> { emit(BackendResult.Error(result.code, result.message)) }
                is BackendResult.Exception -> { emit(BackendResult.Exception(result.e)) }
                is BackendResult.Success -> { emit(BackendResult.Success(result.data.trips)) }
            }
        }.catch { e ->
            emit(BackendResult.Exception(e))
        }
    }

    override fun retrieveGisRoute(
        ctx: String,
        poly: String,
        polyEnc: String
    ): Flow<BackendResult<List<Trip>>> {
        return flow {
            val result = api.getGISRoute(
                ctx = ctx,
                poly = poly.toInt(),
                polyEnc = polyEnc,
                accessId = BuildConfig.API_KEY,
                lang = "es",
                format = "json"
            )
            when(result) {
                is BackendResult.Error -> { emit(BackendResult.Error(result.code, result.message)) }
                is BackendResult.Exception -> { emit(BackendResult.Exception(result.e)) }
                is BackendResult.Success -> { emit(BackendResult.Success(result.data.trips)) }
            }
        }.catch { e ->
            emit(BackendResult.Exception(e))
        }
    }

    override fun retrieveJourneyPos(
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
        return flow {
            val result = api.journeyPos(
                llLat = llLat,
                llLon = llLon,
                urLat = urLat,
                urLon = urLon,
                jid = jid,
                maxJny = maxJny,
                accessId = BuildConfig.API_KEY,
                lang = "es",
                format = "json"
            )
            when(result) {
                is BackendResult.Error -> { emit(BackendResult.Error(result.code, result.message)) }
                is BackendResult.Exception -> { emit(BackendResult.Exception(result.e)) }
                is BackendResult.Success -> { emit(BackendResult.Success(result.data.journeys)) }
            }
        }.catch { e ->
            emit(BackendResult.Exception(e))
        }
    }

    override fun retrieveLocationSearchInBoundingBox(
        llLon: Double,
        llLat: Double,
        urLon: Double,
        urLat: Double,
        positionMode: String,
        maxNo: String
    ): Flow<BackendResult<Unit>> {
        TODO("Not yet implemented")
    }

    override fun retrieveJourneyDetail(): Flow<BackendResult<Unit>> {
        TODO("Not yet implemented")
    }
}