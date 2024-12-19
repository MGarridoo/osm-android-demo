package com.example.osmdemo.core.backend.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Serializable
@Parcelize
data class ProductAtStop(
    @SerializedName("icon")
    val icon: Icon? = null,

    @SerializedName("name")
    val name: String? = null,

    @SerializedName("internalName")
    val internalName: String? = null,

    @SerializedName("line")
    val line: String? = null,

    @SerializedName("lineId")
    val lineId: String? = null,

    @SerializedName("catOut")
    val catOut: String? = null,

    @SerializedName("cls")
    val cls: String? = null,

    @SerializedName("catOutS")
    val catOutS: String? = null,

    @SerializedName("catOutL")
    val catOutL: String? = null
) : Parcelable
