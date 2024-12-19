package com.example.osmdemo.core.backend.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Serializable
@Parcelize
data class CoordLocation(
    @SerializedName("icon")
    val icon: Icon? = null,

    @SerializedName("id")
    val id: String? = null,

    @SerializedName("extId")
    val extId: String? = null,

    @SerializedName("name")
    val name: String? = null,

    @SerializedName("type")
    val type: String? = null,

    @SerializedName("lon")
    val lon: Double? = null,

    @SerializedName("lat")
    val lat: Double? = null,

    @SerializedName("alt")
    val alt: Int? = null
) : Parcelable
