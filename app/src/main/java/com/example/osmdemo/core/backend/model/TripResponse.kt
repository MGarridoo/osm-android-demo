package com.example.osmdemo.core.backend.model

import android.os.Parcelable
import com.example.osmdemo.core.backend.model.LocationResponse.DataWrapper
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import kotlinx.serialization.Serializable

@Parcelize
data class TripResponse(
    @SerializedName("Trip") val trip: List<Trip>?,
    @SerializedName("data") val data: DataWrapper? = null,
    @SerializedName("TechnicalMessages") val technicalMessages: TechnicalMessages,
    @SerializedName("serverVersion") val serverVersion: String,
    @SerializedName("dialectVersion") val dialectVersion: String,
    @SerializedName("requestId") val requestId: String,
    @SerializedName("scrB") val scrB: String?,
    @SerializedName("scrF") val scrF: String?,
    @SerializedName("ResultStatus") val resultStatus: ResultStatus?
) : Parcelable {

    val trips get() = data?.trip ?: trip ?: emptyList()

    @Parcelize
    data class DataWrapper(@SerializedName("Trip") val trip: List<Trip>) : Parcelable
}









































