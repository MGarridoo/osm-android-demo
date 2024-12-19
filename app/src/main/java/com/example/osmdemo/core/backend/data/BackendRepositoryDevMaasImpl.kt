package com.example.osmdemo.core.backend.data

import android.util.Log
import com.example.osmdemo.core.backend.api.DevMaasAPI
import com.example.osmdemo.core.backend.domain.BackendRepository
import com.example.osmdemo.core.backend.model.Journey
import com.example.osmdemo.core.backend.model.JourneyPosResponse
import com.example.osmdemo.core.backend.model.StopOrCoordLocation
import com.example.osmdemo.core.backend.model.Trip
import com.example.osmdemo.core.backend.model.TripResponse
import com.example.osmdemo.core.backend.utils.BackendResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class BackendRepositoryDevMaasImpl @Inject constructor(
    private val api: DevMaasAPI
): BackendRepository {

    override fun retrieveLocationName(
        input: String,
        withProducts: String?,
        type: String?,
        maxNo: String?
    ): Flow<BackendResult<List<StopOrCoordLocation>>> {
        return flow {
            val params = mapOf(
                "input" to input,
                "withProducts" to (withProducts ?: ""),
                "type" to (type ?: ""),
                "maxNo" to (maxNo ?: "")
            )
            when(val result = api.retrieveLocationByName(params = params)) {
                is BackendResult.Error -> { emit(BackendResult.Error(result.code, result.message)) }
                is BackendResult.Exception -> { emit(BackendResult.Exception(result.e)) }
                is BackendResult.Success -> { emit(BackendResult.Success(result.data.locations)) }
            }
        }.catch { e ->
            Log.e("TAG", "retrieveLocationName: $e")
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
            val params = mapOf(
                "originId" to (originId ?: ""),
                "originCoordLong" to (originCoordLong ?: ""),
                "originCoordLat" to (originCoordLat ?: ""),
                "originCoordName" to (originCoordName ?: ""),
                "destId" to (destId ?: ""),
                "destCoordLong" to (destCoordLong ?: ""),
                "destCoordLat" to (destCoordLat ?: ""),
                "destCoordName" to (destCoordName ?: ""),
                "withFreq" to (withFreq ?: ""),
                "originWalk" to (originWalk ?: ""),
                "destWalk" to (destWalk ?: ""),
                "time" to (time ?: ""),
                "poly" to (poly ?: ""),
                "polyEnc" to (polyEnc ?: ""),
                "tripName" to (tripName ?: ""),
                "tripAddress" to (tripAddress ?: ""),
                "ivOnly" to (ivOnly ?: ""),
                "totalCar" to (totalCar ?: ""),
                "totalWalk" to (totalWalk ?: ""),
                "totalBike" to (totalBike ?: ""),
                "date" to (date ?: ""),
                "searchForArrival" to ("${searchForArrival ?: 0}"),
                "groupFilter" to (groupFilter ?: ""),
                "attributes" to (attributes ?: ""),
                "products" to (products ?: ""),
                "passlist" to (passlist?.toString() ?: ""),
                "changeTimePercent" to ("${changeTimePercent ?: ""}"),
                "maxChange" to ("${maxChange ?: 0}"),
                "maxChangeTime" to ("${maxChangeTime ?: 0}"),
                "minChangeTime" to ("${minChangeTime ?: 0}"),
                "rtMode" to (rtMode ?: ""),
                "addChangeTime" to ("${addChangeTime ?: 0}"),
                "tariff" to ("${tariff ?: 0}")
            )
            when(val result = api.retrieveTripSearchDoor2Door(params = params)) {
                is BackendResult.Error -> { emit(BackendResult.Error(result.code, result.message)) }
                is BackendResult.Exception -> { emit(BackendResult.Exception(result.e)) }
                is BackendResult.Success -> { emit(BackendResult.Success(result.data.trips)) }
            }
        }.catch { e ->
            Log.e("TAG", "retrieveTripSearchDoor2Door: $e")
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
        return flow<BackendResult<Unit>> {
           /* val result = api.retrieveLocationByName()


                .searchLocationByName(
                input = input,
                withProducts = withProducts,
                type = type,
                maxNo = maxNo
            )*/


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
            val params = mapOf("ctx" to ctx, "poly" to poly, "polyEnc" to polyEnc)
            when(val result = api.retrieveGisRoute(params = params)) {
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
            val params = mapOf(
                "requestId" to (requestId ?: ""),
                "requestFormat" to (requestFormat ?: ""),
                "jsonCallback" to (jsonCallback ?: ""),
                "llLat" to llLat.toString(),
                "llLon" to llLon.toString(),
                "urLat" to urLat.toString(),
                "urLon" to urLon.toString(),
                "operators" to (operators ?: ""),
                "attributes" to (attributes ?: ""),
                "jid" to (jid ?: ""),
                "lines" to (lines ?: ""),
                "infoTexts" to (infoTexts ?: ""),
                "maxJny" to (maxJny?.toString() ?: ""),
                "periodSize" to (periodSize ?: "35000"),
                "periodStep" to (periodStep ?: "2000"),
                "time" to (time ?: ""),
                "date" to (date ?: ""),
                "positionMode" to (positionMode ?: "CALC")
            )
            when(val result = api.retrieveJourneyPos(params = params)){
                is BackendResult.Error -> { emit(BackendResult.Error(result.code, result.message)) }
                is BackendResult.Exception -> { emit(BackendResult.Exception(result.e)) }
                is BackendResult.Success -> { emit(BackendResult.Success(result.data.journeys))}
            }
        }.catch { e ->
            emit(BackendResult.Exception(e))
        }
    }

    override fun retrieveJourneyDetail(): Flow<BackendResult<Unit>> {
        TODO("Not yet implemented")
    }

}