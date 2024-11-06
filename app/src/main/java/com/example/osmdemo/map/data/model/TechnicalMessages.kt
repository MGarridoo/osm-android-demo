package com.example.osmdemo.map.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class TechnicalMessages(
    @SerializedName("TechnicalMessage") val technicalMessage: List<TechnicalMessage>
) : Parcelable