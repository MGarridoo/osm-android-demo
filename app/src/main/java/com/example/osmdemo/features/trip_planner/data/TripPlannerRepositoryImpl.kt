package com.example.osmdemo.features.trip_planner.data

import com.example.osmdemo.BuildConfig
import com.example.osmdemo.core.backend.model.DepartureResponse
import com.example.osmdemo.core.backend.model.LocationResponse
import com.example.osmdemo.core.backend.model.StopResponse
import com.example.osmdemo.core.backend.utils.BackendResult
import com.example.osmdemo.core.backend.api.API
import com.example.osmdemo.core.backend.domain.BackendRepository
import com.example.osmdemo.core.backend.model.Journey
import com.example.osmdemo.core.backend.model.JourneyPosResponse
import com.example.osmdemo.features.trip_planner.domain.TripPlannerRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class TripPlannerRepositoryImpl @Inject constructor(
    private val api: API,
    private val repo: BackendRepository
) : TripPlannerRepository {

    override fun locationName(location: String): Flow<BackendResult<LocationResponse>> {
        return flow {
            val result = api.searchLocationByName(
                accessId = BuildConfig.API_KEY,
                input = location
            )
            when (result) {
                is BackendResult.Success -> {
                    emit(BackendResult.Success(result.data))
                }

                is BackendResult.Error -> {
                    emit(BackendResult.Error(result.code, result.message))
                }

                is BackendResult.Exception -> {
                    emit(BackendResult.Exception(result.e))
                }
            }
        }.catch { e ->
            emit(BackendResult.Exception(e))
        }
    }

    override fun locationBoundingBox(
        llLon: Double,
        llLat: Double,
        urLon: Double,
        urLat: Double,
        products: Int,
        type: String
    ): Flow<BackendResult<LocationResponse>> {
        return flow {
            val result = api.searchLocationInBoundingBox(
                llLon = llLon,
                llLat = llLat,
                urLon = urLon,
                urLat = urLat,
                products = products,
                type = type,
                accessId = BuildConfig.API_KEY,
            )
            when (result) {
                is BackendResult.Success -> {
                    emit(BackendResult.Success(result.data))
                }

                is BackendResult.Error -> {
                    emit(BackendResult.Error(result.code, result.message))
                }

                is BackendResult.Exception -> {
                    emit(BackendResult.Exception(result.e))
                }
            }
        }.catch { e ->
            emit(BackendResult.Exception(e))
        }
    }

    override fun departureBoard(
        id: String,
        date: String?,
        time: String?,
        maxJourneys: Int?,
        products: Int,
        passlist: Int
    ): Flow<BackendResult<DepartureResponse>> {
        return flow {
            val result = api.getDepartureBoard(
                id = id,
                date = date,
                time = time,
                maxJourneys = maxJourneys,
                products = products,
                passlist = passlist,
                accessId = BuildConfig.API_KEY,
            )
            when (result) {
                is BackendResult.Success -> {
                    emit(BackendResult.Success(result.data))
                }

                is BackendResult.Error -> {
                    emit(BackendResult.Error(result.code, result.message))
                }

                is BackendResult.Exception -> {
                    emit(BackendResult.Exception(result.e))
                }
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

    override fun getJourneyDetails(
        journeyId: String,
        poly: Int?,
        polyEnc: String?
    ): Flow<BackendResult<StopResponse>> {
        return flow {
            val result = api.getJourneyDetails(
                journeyId = journeyId,
                accessId = BuildConfig.API_KEY,
                poly = poly,
                polyEnc = polyEnc,
            )
            when (result) {
                is BackendResult.Success -> {
                    emit(BackendResult.Success(result.data))
                }

                is BackendResult.Error -> {
                    emit(BackendResult.Error(result.code, result.message))
                }

                is BackendResult.Exception -> {
                    emit(BackendResult.Exception(result.e))
                }
            }
        }.catch { e ->
            emit(BackendResult.Exception(e))
        }
    }

}