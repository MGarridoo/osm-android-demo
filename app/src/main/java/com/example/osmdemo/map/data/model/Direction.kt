package com.example.osmdemo.map.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Direction(
    @SerializedName("value") val value: String,
    @SerializedName("flag") val flag: String,
    @SerializedName("routeIdxFrom") val routeIdxFrom: Int,
    @SerializedName("routeIdxTo") val routeIdxTo: Int,
) : Parcelable
