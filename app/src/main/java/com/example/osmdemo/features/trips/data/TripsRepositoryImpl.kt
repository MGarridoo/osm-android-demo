package com.example.osmdemo.features.trips.data

import com.example.osmdemo.core.backend.domain.BackendRepository
import com.example.osmdemo.core.backend.model.Trip
import com.example.osmdemo.core.backend.utils.BackendResult
import com.example.osmdemo.features.trips.domain.TripsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import javax.inject.Inject

class TripsRepositoryImpl @Inject constructor(
    private val repo: BackendRepository,
) : TripsRepository {

    override fun retrieveTripSearchDoor2Door(
        originId: String?,
        originCoordLat: Double?,
        originCoordLong: Double?,
        originCoordName: String?,
        destId: String?,
        destCoordLat: Double?,
        destCoordLong: Double?,
        destCoordName: String?,
        time: String?,
        date: String?,
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
        searchForArrival: Int?,
        tariff: Int?,
        lang: String,
        format: String
    ): Flow<BackendResult<List<Trip>>> {
        return repo.retrieveTripSearchDoor2Door(
            originId = originId,
            originCoordLat = "$originCoordLat",
            originCoordLong = "$originCoordLong",
            originCoordName = "$originCoordName",
            destId = destId,
            destCoordLat = "$destCoordLat",
            destCoordLong = "$destCoordLong",
            destCoordName = destCoordName,
            time = time,
            date = date,
            groupFilter = groupFilter,
            attributes = attributes,
            products = "${products ?: 2034}",
            totalWalk = totalWalk,
            totalBike = totalBike,
            totalCar = totalCar,
            changeTimePercent = changeTimePercent,
            maxChange = maxChange,
            minChangeTime = minChangeTime,
            addChangeTime = addChangeTime,
            maxChangeTime = maxChangeTime,
            rtMode = rtMode,
            poly = "$poly",
            passlist = passlist,
            searchForArrival = searchForArrival,
            tariff = tariff,
        ).catch { e ->
            emit(BackendResult.Exception(e))
        }
    }
}
