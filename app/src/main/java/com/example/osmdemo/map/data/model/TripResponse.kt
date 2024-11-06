package com.example.osmdemo.map.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class TripResponse(
    @SerializedName("Trip") val trip: List<Trip>,
    @SerializedName("TechnicalMessages") val technicalMessages: TechnicalMessages,
    @SerializedName("serverVersion") val serverVersion: String,
    @SerializedName("dialectVersion") val dialectVersion: String,
    @SerializedName("requestId") val requestId: String,
    @SerializedName("scrB") val scrB: String?,
    @SerializedName("scrF") val scrF: String?,
    @SerializedName("ResultStatus") val resultStatus: ResultStatus?
) : Parcelable










































