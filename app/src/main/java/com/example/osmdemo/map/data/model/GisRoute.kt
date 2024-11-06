package com.example.osmdemo.map.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class GisRoute(
    @SerializedName("seg") val segments: List<Segment>?,
    @SerializedName("polylineGroup") val polylineGroup: PolylineGroup?,
    @SerializedName("dist") val dist: Int?,
    @SerializedName("durS") val durS: String?,
    @SerializedName("dirGeo") val dirGeo: Int?,
    @SerializedName("edgeHashS") val edgeHashS: String?,
    @SerializedName("edgeHashR") val edgeHashR: String?
) : Parcelable