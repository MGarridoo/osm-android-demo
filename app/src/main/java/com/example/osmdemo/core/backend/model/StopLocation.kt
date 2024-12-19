package com.example.osmdemo.core.backend.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Serializable
@Parcelize
data class StopLocation(
    @SerializedName("productAtStop")
    val productAtStop: List<ProductAtStop>? = null,

    @SerializedName("altId")
    val altId: List<String>? = null,

    @SerializedName("timezoneOffset")
    val timezoneOffset: Int? = null,

    @SerializedName("id")
    val id: String? = null,

    @SerializedName("extId")
    val extId: String? = null,

    @SerializedName("name")
    val name: String? = null,

    @SerializedName("lon")
    val lon: Double? = null,

    @SerializedName("lat")
    val lat: Double? = null,

    @SerializedName("weight")
    val weight: Int? = null,

    @SerializedName("products")
    val products: Int? = null,

    @SerializedName("minimumChangeDuration")
    val minimumChangeDuration: String? = null,

    @SerializedName("meta")
    val meta: Boolean? = null,
) : Parcelable