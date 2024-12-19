package com.example.osmdemo.core.backend.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import kotlinx.serialization.Serializable

@Parcelize
data class Freq(
    @SerializedName("waitMinimum") val waitMinimum: Int?,
    @SerializedName("waitMaximum") val waitMaximum: Int?,
    @SerializedName("alternativeCount") val alternativeCount: Int?,
    @SerializedName("journey") val journey: List<Journey>?
) : Parcelable