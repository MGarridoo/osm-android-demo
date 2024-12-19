package com.example.osmdemo.core.backend.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Serializable
@Parcelize
data class StopOrCoordLocation(
    @SerializedName("StopLocation")
    val stopLocation: StopLocation? = null,

    @SerializedName("CoordLocation")
    val coordLocation: CoordLocation? = null
) : Parcelable
