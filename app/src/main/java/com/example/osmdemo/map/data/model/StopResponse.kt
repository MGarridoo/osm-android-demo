package com.example.osmdemo.map.data.model

import com.google.gson.annotations.SerializedName

data class StopResponse(
    @SerializedName("Stops")
    val stops: Stops,
    @SerializedName("Names")
    val names: Names,
    @SerializedName("com.example.osmdemo.map.data.model.Product")
    val product: List<Product>,
    @SerializedName("Directions")
    val directions: Directions,
    @SerializedName("JourneyStatus")
    val journeyStatus: String,
    @SerializedName("com.example.osmdemo.map.data.model.PolylineGroup")
    val polylineGroup: PolylineGroup,
    @SerializedName("ServiceDays")
    val serviceDays: List<ServiceDay>,
    @SerializedName("com.example.osmdemo.map.data.model.TechnicalMessages")
    val technicalMessages: TechnicalMessages,
    @SerializedName("serverVersion")
    val serverVersion: String,
    @SerializedName("dialectVersion")
    val dialectVersion: String,
    @SerializedName("planRtTs")
    val planRtTs: String,
    @SerializedName("requestId")
    val requestId: String,
    @SerializedName("reachable")
    val reachable: Boolean,
    @SerializedName("dayOfOperation")
    val dayOfOperation: String,
    @SerializedName("ref")
    val ref: String
) {
    data class Stops(
        @SerializedName("Stop")
        val stop: List<Stop>
    )

    data class Stop(
        @SerializedName("com.example.osmdemo.map.data.model.Notes")
        val notes: Notes? = null,
        val altId: List<String>,
        val name: String,
        val id: String,
        val extId: String,
        val routeIdx: Int,
        val lon: Double,
        val lat: Double,
        val depPrognosisType: String? = null,
        val depTime: String? = null,
        val depDate: String? = null,
        val depDir: String? = null,
        val arrTime: String? = null,
        val arrDate: String? = null,
        val minimumChangeDuration: String? = null
    )

    data class Notes(
        @SerializedName("com.example.osmdemo.map.data.model.Note")
        val note: List<Note>
    )

    data class Note(
        val value: String,
        val key: String,
        val type: String,
        val priority: Int,
        val txtN: String
    )

    data class Names(
        @SerializedName("Name")
        val name: List<Name>
    )

    data class Name(
        @SerializedName("com.example.osmdemo.map.data.model.Product")
        val product: Product,
        val name: String,
        val number: String,
        val category: String,
        val routeIdxFrom: Int,
        val routeIdxTo: Int
    )

    data class Product(
        val icon: Icon,
        val operatorInfo: OperatorInfo,
        val name: String,
        val internalName: String,
        val displayNumber: String,
        val num: String,
        val line: String,
        val lineId: String,
        val catOut: String,
        val catIn: String,
        val catCode: String,
        val cls: String,
        val catOutS: String,
        val catOutL: String,
        val operatorCode: String,
        val operator: String,
        val admin: String,
        val routeIdxFrom: Int,
        val routeIdxTo: Int,
        val matchId: String
    )

    data class Icon(
        val foregroundColor: Color,
        val backgroundColor: Color,
        val res: String
    )

    data class Color(
        val r: Int,
        val g: Int,
        val b: Int,
        val hex: String
    )

    data class OperatorInfo(
        val name: String,
        val nameS: String,
        val nameN: String,
        val nameL: String,
        val id: String
    )

    data class Directions(
        @SerializedName("Direction")
        val direction: List<Direction>
    )

    data class Direction(
        val value: String,
        val flag: String,
        val routeIdxFrom: Int,
        val routeIdxTo: Int
    )

    data class ServiceDay(
        val planningPeriodBegin: String,
        val planningPeriodEnd: String,
        val sDaysR: String,
        val sDaysI: String,
        val sDaysB: String,
        val routeIdxFrom: Int,
        val routeIdxTo: Int
    )

    data class TechnicalMessages(
        @SerializedName("com.example.osmdemo.map.data.model.TechnicalMessage")
        val technicalMessage: List<TechnicalMessage>
    )

    data class TechnicalMessage(
        val value: String,
        val key: String
    )
}
