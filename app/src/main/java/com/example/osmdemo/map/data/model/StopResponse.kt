package com.example.osmdemo.map.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class StopResponse(
    @SerializedName("Stops") val stops: Stops,
    @SerializedName("Names") val names: Names,
    @SerializedName("Product") val product: List<Product>,
    @SerializedName("Directions") val directions: Directions,
    @SerializedName("JourneyStatus") val journeyStatus: String,
    @SerializedName("PolylineGroup") val polylineGroup: PolylineGroup?,
    @SerializedName("ServiceDays") val serviceDays: List<ServiceDays>,
    @SerializedName("lastPos") val lastPos: LastPos,
    @SerializedName("TechnicalMessages") val technicalMessages: TechnicalMessages,
    @SerializedName("serverVersion") val serverVersion: String,
    @SerializedName("dialectVersion") val dialectVersion: String,
    @SerializedName("planRtTs") val planRtTs: String,
    @SerializedName("requestId") val requestId: String,
    @SerializedName("reachable") val reachable: Boolean,
    @SerializedName("dayOfOperation") val dayOfOperation: String,
    @SerializedName("ref") val ref: String
) : Parcelable
