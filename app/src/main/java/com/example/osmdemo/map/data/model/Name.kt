package com.example.osmdemo.map.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Name(
    @SerializedName("Product") val product: Product,
    @SerializedName("name") val name: String,
    @SerializedName("number") val number: String,
    @SerializedName("category") val category: String,
    @SerializedName("routeIdxFrom") val routeIdxFrom: Int,
    @SerializedName("routeIdxTo") val routeIdxTo: Int
) : Parcelable