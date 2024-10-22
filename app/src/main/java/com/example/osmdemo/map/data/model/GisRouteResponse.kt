package com.example.osmdemo.map.data.model

import com.google.gson.annotations.SerializedName

data class GisRouteResponse(
    @SerializedName("com.example.osmdemo.map.data.model.Trip")
    val trip: List<Trip>,
    @SerializedName("com.example.osmdemo.map.data.model.TechnicalMessages")
    val technicalMessages: TechnicalMessages,
    val serverVersion: String,
    val dialectVersion: String,
    val requestId: String
) {
    data class Trip(
        @SerializedName("Origin")
        val origin: Location,
        @SerializedName("Destination")
        val destination: Location,
        @SerializedName("com.example.osmdemo.map.data.model.LegList")
        val legList: LegList,
        val calculation: String,
        @SerializedName("com.example.osmdemo.map.data.model.TripStatus")
        val tripStatus: TripStatus,
        val tripId: String,
        val ctxRecon: String,
        val duration: String,
        val rtDuration: String,
        val checksum: String
    ) {
        data class Location(
            val altId: List<String>,
            val name: String,
            val type: String,
            val id: String,
            val extId: String,
            val lon: Double,
            val lat: Double,
            val time: String,
            val date: String,
            val minimumChangeDuration: String,
            @SerializedName("com.example.osmdemo.map.data.model.Notes")
            val notes: Notes? = null
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

        data class LegList(
            @SerializedName("com.example.osmdemo.map.data.model.Leg")
            val leg: List<Leg>
        )

        data class Leg(
            @SerializedName("Origin")
            val origin: Location,
            @SerializedName("Destination")
            val destination: Location,
            @SerializedName("com.example.osmdemo.map.data.model.GisRef")
            val gisRef: GisRef,
            @SerializedName("com.example.osmdemo.map.data.model.GisRoute")
            val gisRoute: GisRoute,
            @SerializedName("com.example.osmdemo.map.data.model.Product")
            val product: List<Product>,
            val id: String,
            val idx: Int,
            val name: String,
            val type: String,
            val duration: String,
            val dist: Int
        )

        data class GisRef(
            val ref: String
        )

        data class GisRoute(
            val polylineGroup: PolylineGroup,
            val dist: Int,
            val durS: String,
            val dirGeo: Int
        )

        data class PolylineDesc(
            @SerializedName("crd")
            val crd: List<Double>?,
            @SerializedName("delta")
            val delta: Boolean?,
            @SerializedName("dim")
            val dim: Int?,
            @SerializedName("crdEncS")
            val crdEncS: String?
        )

        data class Product(
            val icon: ProductIcon,
            val name: String,
            val internalName: String
        )

        data class ProductIcon(
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

        data class TripStatus(
            val hintCode: Int
        )
    }

    data class TechnicalMessages(
        @SerializedName("com.example.osmdemo.map.data.model.TechnicalMessage")
        val technicalMessage: List<TechnicalMessage>
    )

    data class TechnicalMessage(
        val value: String,
        val key: String
    )
}
