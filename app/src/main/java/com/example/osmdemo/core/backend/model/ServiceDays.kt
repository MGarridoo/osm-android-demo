package com.example.osmdemo.core.backend.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import kotlinx.serialization.Serializable

@Parcelize
data class ServiceDays(
    @SerializedName("planningPeriodBegin") val planningPeriodBegin: String?,
    @SerializedName("planningPeriodEnd") val planningPeriodEnd: String?,
    @SerializedName("sDaysR") val sDaysR: String?,
    @SerializedName("sDaysI") val sDaysI: String?,
    @SerializedName("sDaysB") val sDaysB: String?,
    @SerializedName("routeIdxFrom") val routeIdxFrom: Int?,
    @SerializedName("routeIdxTo") val routeIdxTo: Int?,
) : Parcelable