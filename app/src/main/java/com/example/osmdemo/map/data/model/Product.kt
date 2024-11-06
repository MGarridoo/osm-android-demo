package com.example.osmdemo.map.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Product(
    @SerializedName("icon") val icon: Icon?,
    @SerializedName("operatorInfo") val operatorInfo: OperatorInfo?,
    @SerializedName("name") val name: String?,
    @SerializedName("internalName") val internalName: String?,
    @SerializedName("displayNumber") val displayNumber: String?,
    @SerializedName("num") val num: String?,
    @SerializedName("line") val line: String?,
    @SerializedName("lineId") val lineId: String?,
    @SerializedName("catOut") val catOut: String?,
    @SerializedName("catIn") val catIn: String?,
    @SerializedName("catCode") val catCode: String?,
    @SerializedName("cls") val cls: String?,
    @SerializedName("catOutS") val catOutS: String?,
    @SerializedName("catOutL") val catOutL: String?,
    @SerializedName("operatorCode") val operatorCode: String?,
    @SerializedName("operator") val operator: String?,
    @SerializedName("admin") val admin: String?,
    @SerializedName("matchId") val matchId: String?
) : Parcelable