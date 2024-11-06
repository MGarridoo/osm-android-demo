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
import androidx.activity.addCallback
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.osmdemo.BuildConfig
import com.example.osmdemo.core.backend.BackendResult
import com.example.osmdemo.databinding.FragmentMapBinding
import com.example.osmdemo.map.data.model.GisRoute
import com.example.osmdemo.map.data.model.PolylineGroup
import com.example.osmdemo.map.domain.repository.MapRepository
import com.example.osmdemo.trips_details.presentation.TripsDetailsFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.overlay.Marker
import org.osmdroid.views.overlay.Polyline
import javax.inject.Inject

@AndroidEntryPoint
class MapFragment : Fragment() {

    private var _binding: FragmentMapBinding? = null
    private val binding get() = _binding!!

    private val args: MapFragmentArgs by navArgs() // Argumentos recibidos en la navegación

    private val accessId = BuildConfig.API_KEY // Token del API

    private val geoPoints = mutableListOf<GeoPoint>() // Listado de geoPoints

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
        setupOnTripDetailsClick()
        setupOnBackPressed()
        setupOnMap()
        if (args.trip != null) { lifecycleScope.launch { drawCompleteRoute(args.trip!!) } }
    }

    // Botón de retroceso
    private fun setupOnBackPressed() {
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            findNavController().popBackStack()
        }
    }

    // Configuración de mapa
    private fun setupOnMap() {
        val startPoint = GeoPoint(4.620082, -74.082061)

        binding.map.apply {
            setTileSource(TileSourceFactory.MAPNIK)
            setMultiTouchControls(true)
        }

        binding.map.controller.apply {
            setZoom(14.0)
            setCenter(startPoint)
        }
    }

    // Obtener información detallada de la ruta
    private fun setupOnTripDetailsClick() {
        lifecycleScope.launch {
            val trip = args.trip!!
            binding.floatingButton.setOnClickListener {
                val bottomSheet = TripsDetailsFragment.newInstance(trip)
                bottomSheet.show(parentFragmentManager, "TripDetailsBottomSheet")
            }
        }
    }

    // Dibujar ruta completa (transporte y ruta a pie)
    private suspend fun drawCompleteRoute(trip: Trip) {
        val legs = trip.legList.leg

        legs.forEachIndexed { index, leg ->
            //val stops = leg.freq?.journey
            val startPoint = GeoPoint(leg.origin.lat, leg.origin.lon)
            val endPoint = GeoPoint(leg.destination.lat, leg.destination.lon)
            val ref = leg.gisRef?.ref

            if (index == 0) {
                //placeMarker(startPoint.latitude, startPoint.longitude, leg.origin.name)
            }

            //placeMarker(endPoint.latitude, endPoint.longitude, leg.destination.name)

            if (leg.polylineGroup != null) {
                // Si hay polilínea disponible, dibujarla
                drawPolylineFromGroup(leg.polylineGroup)
            } else {
                when (leg.type) {
                    "WALK" -> { if (ref != null) { getGisRouteAndDraw(ref) } }
                    "JNY" -> { }
                }
            }
        }

        // Refrescar el mapa
        binding.map.invalidate()
    }

    // Dibujar polylines que representan la ruta del transporte
    private fun drawPolylineFromGroup(polylineGroup: PolylineGroup) {
        val geoPoints = mutableListOf<GeoPoint>()

        polylineGroup.polylineDesc.forEach { polylineDesc ->
            for (i in polylineDesc.crd!!.indices step 2) {
                val lon = polylineDesc.crd[i] // Longitud
                val lat = polylineDesc.crd[i + 1] // Latitud
                geoPoints.add(GeoPoint(lat, lon))
            }

            val polyline = Polyline().apply {
                setPoints(geoPoints)
                outlinePaint.color = Color.RED
                outlinePaint.strokeWidth = 10.0f
            }

            binding.map.overlays.add(polyline)
        }
    }

    // Consulta para obtener ruta de caminata
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
                if (leg.gisRoute != null) { drawGisRoute(leg.gisRoute) }
            }
            is BackendResult.Error -> Log.e("TAG", "Error fetching GIS route")
            is BackendResult.Exception -> Log.e("TAG", "Exception fetching GIS route")
        }
    }

    // Dibujar polylines que representan la ruta de caminata
    private fun drawGisRoute(gisRoute: GisRoute) {
        val geoPoints = mutableListOf<GeoPoint>()

        gisRoute.polylineGroup!!.polylineDesc.forEach { polylineGroup ->
            polylineGroup.crd.let { coordinates ->
                for (i in coordinates!!.indices step 2) {
                    val lon = coordinates[i]  // Longitud
                    val lat = coordinates[i + 1]  // Latitud
                    geoPoints.add(GeoPoint(lat, lon))
                }
            }
        }

        val polyline = Polyline().apply {
            setPoints(geoPoints)
            outlinePaint.color = Color.BLUE
            outlinePaint.strokeWidth = 10.0f
            outlinePaint.pathEffect = DashPathEffect(floatArrayOf(1f, 1f), 0f)
        }

        binding.map.overlays.add(polyline)
    }

    // Colocar marcador en el mapa
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
