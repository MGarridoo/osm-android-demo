package com.example.osmdemo.core.backend.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import kotlinx.serialization.Serializable

@Parcelize
data class JourneyPathItem(
    @SerializedName("progressInTime") val progressInTime: Int,
    @SerializedName("progressInPercent") val progressInPercent: Int,
    @SerializedName("progressAbs") val progressAbs: Int,
    @SerializedName("fromLocationId") val fromLocationId: String,
    @SerializedName("toLocationId") val toLocationId: String,
    @SerializedName("dirGeo") val dirGeo: Int,
    @SerializedName("state") val state: String
) : Parcelable


