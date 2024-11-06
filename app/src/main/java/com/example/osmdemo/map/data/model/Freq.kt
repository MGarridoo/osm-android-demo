package com.example.osmdemo.map.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Freq(
    @SerializedName("waitMinimum") val waitMinimum: Int?,
    @SerializedName("waitMaximum") val waitMaximum: Int?,
    @SerializedName("alternativeCount") val alternativeCount: Int?,
    @SerializedName("journey") val journey: List<Journey>?
) : Parcelable