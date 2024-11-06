package com.example.osmdemo.map.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class LastPos(
   @SerializedName("lon") val lon: Double,
   @SerializedName("lat") val lat: Double
) : Parcelable
