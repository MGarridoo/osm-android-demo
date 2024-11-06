package com.example.osmdemo.map.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Color(
    @SerializedName("r") val red: Int?,
    @SerializedName("g") val green: Int?,
    @SerializedName("b") val blue: Int?,
    @SerializedName("hex") val hex: String?
) : Parcelable