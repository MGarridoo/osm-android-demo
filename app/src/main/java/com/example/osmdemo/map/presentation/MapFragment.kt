package com.example.osmdemo.map.presentation

import android.graphics.Bitmap
import android.graphics.Canvas
import com.example.osmdemo.map.data.model.Trip
import android.graphics.Color
import android.graphics.DashPathEffect
import android.graphics.Paint
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.osmdemo.BuildConfig
import com.example.osmdemo.R
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

    //private val geoPoints = mutableListOf<GeoPoint>() // Listado de geoPoints

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
            val startPoint = GeoPoint(leg.origin.lat, leg.origin.lon)
            val endPoint = GeoPoint(leg.destination.lat, leg.destination.lon)
            val ref = leg.gisRef?.ref

            // Colocar marcador en el punto de inicio (Punto A)
            if (index == 0) {
                placeMarker(
                    lat = startPoint.latitude,
                    lon = startPoint.longitude,
                    type = "A",
                    title = "Punto A: ${leg.origin.name}"
                )
            }

            // Colocar marcador en el punto de destino (Punto B)
            if (index == legs.size - 1) {
                placeMarker(
                    lat = endPoint.latitude,
                    lon = endPoint.longitude,
                    type = "B",
                    title = "Punto B: ${leg.destination.name}"
                )
            }

            // Iterar sobre las paradas (stops) del leg
            leg.stops?.stop?.forEach { stop ->
                val geoPoint = GeoPoint(stop.lat!!, stop.lon!!)
                placeCircularStopMarker(
                    lat = geoPoint.latitude,
                    lon = geoPoint.longitude,
                    title = stop.name ?: "Parada"
                )
            }

            // Si hay polilínea disponible, dibujarla (ruta de BUS)
            if (leg.polylineGroup != null) {
                drawPolylineFromGroup(leg.polylineGroup)
            } else {
                when (leg.type) {
                    "WALK" -> {
                        if (ref != null) {
                            // Se realiza petición de GisRoute para dibujar ruta a pie
                            getGisRouteAndDraw(ref)
                        }
                    }
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
                Log.d("TAG", "getGisRouteAndDraw: walk -> ${gisRouteResult.data.trip.size}")


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
    private fun placeMarker(lat: Double, lon: Double, type: String, title: String) {
        val marker = Marker(binding.map)
        val icon = if (type == "A") R.drawable.haf_prod_marker_entire_a else R.drawable.haf_prod_marker_entire_b
        val vectorDrawable = ContextCompat.getDrawable(requireContext(), icon)

        marker.position = GeoPoint(lat, lon)
        marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
        marker.title = title

        if (vectorDrawable != null) {
            val desiredWidth = 100
            val desiredHeight = 100
            vectorDrawable.setBounds(0, 0, desiredWidth, desiredHeight)

            // Convertir el VectorDrawable en Bitmap para establecer el tamaño
            val bitmap = Bitmap.createBitmap(desiredWidth, desiredHeight, Bitmap.Config.ARGB_8888)
            val canvas = Canvas(bitmap)
            vectorDrawable.draw(canvas)

            // Asignar el BitmapDrawable al marcador
            marker.icon = BitmapDrawable(resources, bitmap)
        }

        binding.map.overlays.add(marker)
    }

    // Colocar marcador de las paradas en el mapa
    private fun placeCircularStopMarker(lat: Double, lon: Double, title: String) {
        val marker = Marker(binding.map)
        marker.position = GeoPoint(lat, lon)
        marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_CENTER)
        marker.title = title

        // Crear un icono circular rojo con un centro blanco
        val outerRadius = 30  // Tamale del círculo rojo
        val innerRadius = 15  // Tamaño del punto blanco en el centro
        val bitmap = Bitmap.createBitmap(outerRadius * 2, outerRadius * 2, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)

        // Pintura para el círculo rojo
        val outerPaint = Paint().apply {
            color = Color.RED
            style = Paint.Style.FILL
            isAntiAlias = true
        }
        // Pintura para el punto blanco en el centro
        val innerPaint = Paint().apply {
            color = Color.WHITE
            style = Paint.Style.FILL
            isAntiAlias = true
        }

        // Dibujar el círculo rojo exterior
        canvas.drawCircle(outerRadius.toFloat(), outerRadius.toFloat(), outerRadius.toFloat(), outerPaint)
        // Dibujar el punto blanco en el centro
        canvas.drawCircle(outerRadius.toFloat(), outerRadius.toFloat(), innerRadius.toFloat(), innerPaint)

        // Asignar el bitmap como icono del marcador
        marker.icon = BitmapDrawable(resources, bitmap)

        binding.map.overlays.add(marker)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
