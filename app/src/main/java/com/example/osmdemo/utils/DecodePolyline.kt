package com.example.osmdemo.utils

import org.osmdroid.util.GeoPoint

/**
 * Decodifica una polyline codificada (formato de Google) en una lista de puntos (lat/lng).
 *
 * @param encoded La cadena polyline codificada.
 * @return Una lista de [GeoPoint] representando el camino decodificado.
 * @throws IllegalArgumentException Si la cadena no es válida.
 */
fun decodePolyline(encoded: String): List<GeoPoint> {
    val poly = ArrayList<GeoPoint>()
    var index = 0
    val length = encoded.length
    var lat = 0
    var lng = 0

    while (index < length) {
        // Decodificar la latitud
        val (deltaLat, newIndexLat) = decodeValue(encoded, index)
        index = newIndexLat
        lat += deltaLat

        // Verificamos que no nos hayamos salido del rango
        if (index >= length) {
            throw IllegalArgumentException("Polyline mal formada: faltan datos para longitud")
        }

        // Decodificar la longitud
        val (deltaLng, newIndexLng) = decodeValue(encoded, index)
        index = newIndexLng
        lng += deltaLng

        val latitude = lat / 1E5
        val longitude = lng / 1E5
        poly.add(GeoPoint(latitude, longitude))
    }

    return poly
}

/**
 * Decodifica un valor (lat o lng) desde la cadena. Devuelve el valor decodificado y el nuevo índice.
 */
private fun decodeValue(encoded: String, startIndex: Int): Pair<Int, Int> {
    var index = startIndex
    val length = encoded.length
    var result = 0
    var shift = 0
    var b: Int

    while (true) {
        if (index >= length) {
            throw IllegalArgumentException("Polyline mal formada: final inesperado")
        }

        b = encoded[index++].code - 63
        result = result or ((b and 0x1f) shl shift)
        shift += 5

        if (b < 0x20) break
    }

    val decoded = if ((result and 1) != 0) (result shr 1).inv() else (result shr 1)
    return decoded to index
}
