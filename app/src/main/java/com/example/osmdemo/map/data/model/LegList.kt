package com.example.osmdemo.map.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class LegList(
    @SerializedName("Leg") val leg: List<Leg>
) : Parcelable