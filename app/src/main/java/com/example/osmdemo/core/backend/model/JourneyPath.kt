package com.example.osmdemo.core.backend.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import kotlinx.serialization.Serializable

@Parcelize
data class JourneyPath(
    @SerializedName("JourneyPathItem") val journeyPathItem: List<JourneyPathItem>,
    @SerializedName("Location") val location: List<Location>,
    @SerializedName("PolylineGroup") val polylineGroup: PolylineGroup
) : Parcelable
