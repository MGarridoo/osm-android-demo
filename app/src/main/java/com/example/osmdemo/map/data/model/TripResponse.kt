package com.example.osmdemo.map.data.model

import com.google.gson.annotations.SerializedName

data class TripResponse(
    @SerializedName("Trip") val trip: List<Trip>,
    @SerializedName("TechnicalMessages") val technicalMessages: TechnicalMessages,
    @SerializedName("serverVersion") val serverVersion: String,
    @SerializedName("dialectVersion") val dialectVersion: String,
    @SerializedName("requestId") val requestId: String
)

data class Trip(
    @SerializedName("Origin") val origin: Location,
    @SerializedName("Destination") val destination: Location,
    @SerializedName("ServiceDays") val serviceDays: List<ServiceDays>,
    @SerializedName("Freq") val freq: Freq,
    @SerializedName("LegList") val legList: LegList,
    @SerializedName("calculation") val calculation: String,
    @SerializedName("TripStatus") val tripStatus: TripStatus,
    @SerializedName("tripId") val tripId: String,
    @SerializedName("ctxRecon") val ctxRecon: String,
    @SerializedName("duration") val duration: String,
    @SerializedName("checksum") val checksum: String
)

data class Location(
    @SerializedName("Notes") val notes: Notes,
    @SerializedName("altId") val altId: List<String>,
    @SerializedName("name") val name: String,
    @SerializedName("type") val type: String,
    @SerializedName("id") val id: String,
    @SerializedName("extId") val extId: Int,
    @SerializedName("lon") val lon: Double,
    @SerializedName("lat") val lat: Double,
    @SerializedName("alt") val alt: Int,
    @SerializedName("time") val time: String,
    @SerializedName("date") val date: String,
    @SerializedName("minimumChangeDuration") val minimumChangeDuration: String,
)

data class ServiceDays(
    @SerializedName("planningPeriodBegin") val planningPeriodBegin: String,
    @SerializedName("planningPeriodEnd") val planningPeriodEnd: String,
    @SerializedName("sDaysR") val sDaysR: String,
    @SerializedName("sDaysI") val sDaysI: String,
    @SerializedName("sDaysB") val sDaysB: String
)

data class Freq(
    @SerializedName("waitMinimum") val waitMinimum: Int
)

data class LegList(
    @SerializedName("Leg") val leg: List<Leg>
)

data class Leg(
    @SerializedName("Origin") val origin: Location,
    @SerializedName("Destination") val destination: Location,
    @SerializedName("GisRef") val gisRef: GisRef,
    @SerializedName("GisRoute") val gisRoute: GisRoute,
    @SerializedName("Product") val product: List<Product>,
    @SerializedName("PolylineGroup") val polylineGroup: PolylineGroup?,
    @SerializedName("id") val id: String,
    @SerializedName("idx") val idx: Int,
    @SerializedName("name") val name: String,
    @SerializedName("type") val type: String,
    @SerializedName("duration") val duration: String,
    @SerializedName("dist") val dist: Int
)

data class GisRef(
    @SerializedName("ref") val ref: String
)

data class GisRoute(
    @SerializedName("seg") val segments: List<Segment>,
    @SerializedName("polylineGroup") val polylineGroup: PolylineGroup,
    @SerializedName("dist") val dist: Int,
    @SerializedName("durS") val durS: String,
    @SerializedName("dirGeo") val dirGeo: Int,
    @SerializedName("edgeHashS") val edgeHashS: String,
    @SerializedName("edgeHashR") val edgeHashR: String
)

data class Segment(
    @SerializedName("Notes") val notes: Notes,
    @SerializedName("Edge") val edges: List<Edge>,
    @SerializedName("name") val name: String,
    @SerializedName("rType") val routeType: String,
    @SerializedName("man") val man: String,
    @SerializedName("manTx") val manTx: String,
    @SerializedName("ori") val ori: String,
    @SerializedName("polyS") val polyStart: Int,
    @SerializedName("polyE") val polyEnd: Int,
    @SerializedName("dist") val dist: Int,
    @SerializedName("durS") val durS: String
)

data class Notes(
    @SerializedName("Note") val note: List<Note>
)

data class Note(
    @SerializedName("value") val value: String,
    @SerializedName("key") val key: String,
    @SerializedName("type") val type: String,
    @SerializedName("txtN") val txtN: String
)

data class Edge(
    @SerializedName("edgeId") val edgeId: String,
    @SerializedName("graphId") val graphId: String
)

data class PolylineGroup(
    @SerializedName("polylineDesc") val polylineDesc: List<PolylineDesc>
)

data class PolylineDesc(
    @SerializedName("crd") val crd: List<Double>,
    @SerializedName("delta") val delta: Boolean,
    @SerializedName("dim") val dim: Int,
    @SerializedName("crdEncS") val crdEncS: String
)

data class Product(
    @SerializedName("icon") val icon: Icon,
    @SerializedName("name") val name: String,
    @SerializedName("internalName") val internalName: String
)

data class Icon(
    @SerializedName("foregroundColor") val foregroundColor: Color,
    @SerializedName("backgroundColor") val backgroundColor: Color,
    @SerializedName("res") val resource: String
)

data class Color(
    @SerializedName("r") val red: Int,
    @SerializedName("g") val green: Int,
    @SerializedName("b") val blue: Int,
    @SerializedName("hex") val hex: String
)

data class TripStatus(
    @SerializedName("hintCode") val hintCode: Int
)

data class TechnicalMessages(
    @SerializedName("TechnicalMessage") val technicalMessage: List<TechnicalMessage>
)

data class TechnicalMessage(
    @SerializedName("value") val value: String,
    @SerializedName("key") val key: String
)
