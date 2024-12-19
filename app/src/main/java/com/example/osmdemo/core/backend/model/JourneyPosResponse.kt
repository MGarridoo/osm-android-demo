package com.example.osmdemo.core.backend.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class JourneyPosResponse(
    @SerializedName("data") val data: DataWrapper? = null,
    @SerializedName("Journey") val journey: List<Journey>?,
    @SerializedName("TechnicalMessages") val technicalMessages: TechnicalMessages? = null,
    @SerializedName("serverVersion") val serverVersion: String,
    @SerializedName("dialectVersion") val dialectVersion: String,
    @SerializedName("planRtTs") val planRtTs: String,
    @SerializedName("requestId") val requestId: String,
    @SerializedName("planningPeriodBegin") val planningPeriodBegin: String,
    @SerializedName("planningPeriodEnd") val planningPeriodEnd: String,
) : Parcelable {

    val journeys get() = data?.journey ?: journey ?: emptyList()

    @Parcelize
    data class DataWrapper(@SerializedName("Journey") val journey: List<Journey>?) : Parcelable
}
