package com.example.osmdemo.map.presentation

import com.example.osmdemo.map.data.model.Trip
import android.graphics.Color
import android.graphics.DashPathEffect
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import com.example.osmdemo.core.backend.BackendResult
import com.example.osmdemo.databinding.FragmentMapBinding
import com.example.osmdemo.map.data.model.GisRoute
import com.example.osmdemo.map.data.model.PolylineGroup
import com.example.osmdemo.map.domain.repository.MapRepository
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.overlay.Marker
import org.osmdroid.views.overlay.Polyline
import javax.inject.Inject
import com.example.osmdemo.BuildConfig


@AndroidEntryPoint
class MapFragment : Fragment() {

    private var _binding: FragmentMapBinding? = null
    private val binding get() = _binding!!

    private val accessId = BuildConfig.ACCESS_ID

    @Inject
    lateinit var map: MapRepository

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMapBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupMap()

        // Aquí se inicia el proceso de búsqueda de las rutas
        lifecycleScope.launch {
            val originName = "La Parrilla Moderna, Transversal 93, UPZ Álamos"
            val destName = "El Campin, Avenida Carrera 14, UPZ Chapinero"

            // Obtener ubicación de origen y destino
            val originLocation = map.searchLocationByName(
                input = originName,
                type = "S",
                accessId = accessId
            )

            val destLocation = map.searchLocationByName(
                input = destName,
                type = "S",
                accessId = accessId
            )

            if (originLocation is BackendResult.Success && destLocation is BackendResult.Success) {
                val routesResult = map.searchTrip(
                    originId = "980010843",
                    destId = "980035049",
                    poly = 1,
                    maxChange = 0,
                    accessId = accessId
                )

                when (routesResult) {
                    is BackendResult.Success -> { drawCompleteRoute(routesResult.data.trip) }
                    is BackendResult.Error -> Log.e("TAG", "Error in fetching routes")
                    is BackendResult.Exception -> Log.e("TAG", "Exception in fetching routes")
                }
            }
        }
    }

    private fun setupMap() {
        val map = binding.map
        map.setTileSource(TileSourceFactory.MAPNIK)
        map.setMultiTouchControls(true)
        val mapController = map.controller
        mapController.setZoom(14.0)
        val startPoint = GeoPoint(4.620082, -74.082061)
        mapController.setCenter(startPoint)
    }

    private suspend fun drawCompleteRoute(tripList: List<Trip>) {
        val trip = tripList[0]
        val legs = trip.legList.leg

        legs.forEachIndexed { index, leg ->
            val startPoint = GeoPoint(leg.origin.lat, leg.origin.lon)
            val endPoint = GeoPoint(leg.destination.lat, leg.destination.lon)

            if (leg.polylineGroup != null) {
                // Si hay polilínea disponible, dibujarla
                drawPolylineFromGroup(leg.polylineGroup)
            } else {
                when (leg.type) {
                    "WALK" -> { getGisRouteAndDraw(leg.gisRef.ref) }
                    "JNY" -> { }
                }
            }
        }

        // Refrescar el mapa
        binding.map.invalidate()
    }

    private fun drawPolylineFromGroup(polylineGroup: PolylineGroup) {
        val geoPoints = mutableListOf<GeoPoint>()

        polylineGroup.polylineDesc.forEach { polylineDesc ->
            for (i in polylineDesc.crd.indices step 2) {
                val lon = polylineDesc.crd[i] // Longitud
                val lat = polylineDesc.crd[i + 1] // Latitud
                geoPoints.add(GeoPoint(lat, lon))
            }

            val polyline = Polyline().apply {
                setPoints(geoPoints)
                outlinePaint.color = Color.RED
                outlinePaint.strokeWidth = 6.0f
            }

            binding.map.overlays.add(polyline)
        }
    }

    private suspend fun getGisRouteAndDraw(gisRef: String) {
        val gisRouteResult = map.getGISRoute(
            ctx = gisRef,
            poly = 1,
            accessId = accessId,
        )

        when (gisRouteResult) {
            is BackendResult.Success -> {
                val trip = gisRouteResult.data.trip[0]
                val leg = trip.legList.leg[0]
                drawGisRoute(leg.gisRoute)
            }
            is BackendResult.Error -> Log.e("TAG", "Error fetching GIS route")
            is BackendResult.Exception -> Log.e("TAG", "Exception fetching GIS route")
        }
    }

    private fun drawGisRoute(gisRoute: GisRoute) {
        val geoPoints = mutableListOf<GeoPoint>()

        gisRoute.polylineGroup.polylineDesc.forEach { polylineGroup ->
            polylineGroup.crd.let { coordinates ->
                for (i in coordinates.indices step 2) {
                    val lon = coordinates[i]  // Longitud
                    val lat = coordinates[i + 1]  // Latitud
                    geoPoints.add(GeoPoint(lat, lon))
                }
            }
        }

        val polyline = Polyline().apply {
            setPoints(geoPoints)
            outlinePaint.color = Color.BLUE
            outlinePaint.strokeWidth = 6.0f
            outlinePaint.pathEffect = DashPathEffect(floatArrayOf(10f, 10f), 0f)
        }

        binding.map.overlays.add(polyline)
    }

    private fun placeMarker(lat: Double, lon: Double, title: String) {
        val marker = Marker(binding.map)
        marker.position = GeoPoint(lat, lon)
        marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
        marker.title = title
        binding.map.overlays.add(marker)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
