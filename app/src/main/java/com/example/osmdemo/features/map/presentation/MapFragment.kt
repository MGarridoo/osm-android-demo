package com.example.osmdemo.features.map.presentation

import android.graphics.Bitmap
import android.graphics.Canvas
import com.example.osmdemo.core.backend.model.Trip
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.graphics.PathDashPathEffect
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.osmdemo.R
import com.example.osmdemo.databinding.FragmentMapBinding
import com.example.osmdemo.core.backend.model.GisRoute
import com.example.osmdemo.core.backend.model.Journey
import com.example.osmdemo.core.backend.model.PolylineDesc
import com.example.osmdemo.core.backend.model.PolylineGroup
import com.example.osmdemo.features.trips_details.presentation.TripsDetailsFragment
import com.example.osmdemo.shared.extensions.createResizedDrawable
import com.example.osmdemo.shared.extensions.toColorInt
import com.example.osmdemo.utils.decodePolyline
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.overlay.Marker
import org.osmdroid.views.overlay.Polyline

@AndroidEntryPoint
class MapFragment : Fragment() {

    private var _binding: FragmentMapBinding? = null
    private val binding get() = _binding!!

    private val busMarkers = mutableMapOf<String, Marker>() // Almacena todos los marcadores por bus

    private val args: MapFragmentArgs by navArgs() // Argumentos recibidos en la navegación

    private val viewModel by viewModels<MapViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMapBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onDetailsClick()
        onBackPressed()
        setupDrawRoute()
        setupJourneyPositions()
        observeJourneyPositions()
    }

    /**
     * Función para dibujar toda la rota
     * */
    private fun setupDrawRoute() {
        if (args.trip != null) {
            drawCompleteRoute(args.trip!!)
        }
    }

    private fun setupJourneyPositions() {
        // Actualización periódica de las posiciones

    }

    /**
     * Observar las posiciones de los buses
     */
    private fun observeJourneyPositions() {
        lifecycleScope.launch {
            viewModel.state.collectLatest { state ->
                state.journeys?.let { journeys ->
                    binding.map.invalidate()
                    updateBusMarkers(journeys)
                }
            }
        }
    }

    private fun updateBusMarkers(journeys: List<Journey>) {
        journeys.forEach { journey ->
            val bus = journey.name ?: "Bus"
            val polylineGroup = journey.journeyPath?.polylineGroup
            val crdEncYX = polylineGroup?.polylineDesc?.firstOrNull()?.crdEncYX

            if (polylineGroup != null && crdEncYX != null) {
                Log.d("TAG", "updateBusMarkers: Antes de desencriptar --> $crdEncYX")

                //drawBusRoute(polylineGroup.polylineDesc)
                val decodedPoints = decodePolyline(crdEncYX)

                Log.d("TAG", "updateBusMarkers: Despues de desencriptar --> $decodedPoints")

                simulateBusMovement(decodedPoints, bus)
            }

            Log.d("TAG", "updateBusMarkers: journey -> $journey")
        }
    }

    private fun drawBusRoute(polylineDesc: List<PolylineDesc>) {
        val geoPoints = mutableListOf<GeoPoint>()

        // Decodificar coordenadas de la polilínea
        polylineDesc.forEach { desc ->
            val crdEncYX = desc.crdEncYX // Coordenadas codificadas
            if (crdEncYX != null) {
                val decodedPoints = decodePolyline(crdEncYX)
                geoPoints.addAll(decodedPoints)
            }
        }

        // Dibujar la ruta en el mapa
        val polyline = Polyline().apply {
            setPoints(geoPoints)
            outlinePaint.color = Color.BLUE
            outlinePaint.strokeWidth = 8.0f
        }
        binding.map.overlays.add(polyline)
        binding.map.invalidate()
    }

    /**
     * Función para simular el movimiento del bus
     * @param geoPoints Listado de Latitud y Logintud decoficados
     * @param bus Nombre del bus
     * */
    private fun simulateBusMovement(geoPoints: List<GeoPoint>, bus: String) {
        val iconBus = createResizedDrawable(
            context = requireContext(),
            drawableId = R.drawable.prod_bus,
            scaleFactor = 0.3f
        )

        // Verifica si ya existe el marcador para este bus
        val currentBusMarker = busMarkers.getOrPut(bus) {
            // Crear marcador si no existe
            Marker(binding.map).apply {
                position = geoPoints.last()
                title = bus
                icon = iconBus
                binding.map.overlays.add(this) // Añadir el marcador al mapa
            }
        }

        // Lanzar una corrutina que actualice la posición de este bus
        lifecycleScope.launch {
            for (point in geoPoints) {
                delay(10000) // Intervalo de tiempo entre puntos (simulación)

                // Mover el marcador del bus
                currentBusMarker.position = point
                binding.map.invalidate()
            }
        }
    }

    /**
     * Botón de retroceso
     */
    private fun onBackPressed() {
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            findNavController().popBackStack()
        }
    }

    /**
     * Configuración de mapa
     */
    private fun setupOnMap(geoPoint: GeoPoint) {
        binding.map.apply {
            setTileSource(TileSourceFactory.MAPNIK)
            setMultiTouchControls(true)
        }

        binding.map.controller.apply {
            setZoom(14.0)
            setCenter(geoPoint)
        }
    }

    /**
     * Obtener información detallada de la ruta
     * */
    private fun onDetailsClick() {
        lifecycleScope.launch {
            binding.floatingButton.setOnClickListener {
                val trip = args.trip!!
                val bottomSheet = TripsDetailsFragment.newInstance(trip)
                bottomSheet.show(parentFragmentManager, "TripDetailsBottomSheet")
            }
        }
    }

    /**
     * Dibujar ruta completa (transporte y ruta a pie)
     * */
    private fun drawCompleteRoute(trip: Trip) {
        var startPoint: GeoPoint? = null
        var endPoint: GeoPoint? = null
        val journeyRefs = mutableListOf<String>() // journeys ids
        val lines = mutableListOf<String>() // Lineas de buses
        val legs = trip.legList.leg

        legs.forEachIndexed { index, leg ->
            val currentStartPoint = GeoPoint(leg.origin.lat, leg.origin.lon)
            val currentEndPoint = GeoPoint(leg.destination.lat, leg.destination.lon)
            val ref = leg.gisRef?.ref

            // Agregar el JourneyDetailRef.ref si el leg es de tipo JNY
            if (leg.type == "JNY" && leg.journeyDetailRef?.ref != null) {
                journeyRefs.add(leg.journeyDetailRef.ref)
            }

            // Agregar las lineas del bus si el leg es de tipo JNY
            if (leg.type == "JNY" && leg.product?.get(0)?.line != null) {
                //line.add(leg.product[0].line!!)
            }

            // Colocar marcador en el punto de inicio (Punto A)
            if (index == 0) {
                startPoint = currentStartPoint
                placeMarker(
                    lat = currentStartPoint.latitude,
                    lon = currentStartPoint.longitude,
                    type = "A",
                    title = "Punto A: ${leg.origin.name}"
                )
            }

            // Colocar marcador en el punto de destino (Punto B)
            if (index == legs.size - 1) {
                endPoint = currentEndPoint
                placeMarker(
                    lat = currentEndPoint.latitude,
                    lon = currentEndPoint.longitude,
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
                val backgroundColor = leg.product?.get(0)?.icon?.backgroundColor?.hex
                val polylineGroup = leg.polylineGroup
                drawPolylineFromGroup(polylineGroup, backgroundColor)
            } else {
                when (leg.type) {
                    "WALK" -> {
                        if (ref != null) {
                            // Se realiza petición de GisRoute para dibujar ruta a pie
                            getGisRouteAndDraw(ref)
                        }
                    }

                    "TRSF" -> {
                        val backgroundColor = leg.product?.get(0)?.icon?.backgroundColor?.hex
                        val polylineGroup = leg.gisRoute?.polylineGroup

                        if (polylineGroup != null) {
                            drawPolylineFromGroup(polylineGroup, backgroundColor)
                        }
                    }

                    "JNY" -> {}
                }
            }
        }

        // Centrar el mapa en el área que incluye A y B
        if (startPoint != null && endPoint != null) {
            centerMapBetweenPoints(startPoint!!, endPoint!!)
        }

        // Refrescar el mapa
        binding.map.invalidate()

        // Obtener los cuadrantes
        binding.map.addOnFirstLayoutListener { _, _, _, _, _ ->
            val boundingBox = binding.map.boundingBox
            val llLat = boundingBox.latSouth
            val llLon = boundingBox.lonWest
            val urLat = boundingBox.latNorth
            val urLon = boundingBox.lonEast
            val linesS = lines.joinToString(",")
            val jid = journeyRefs.joinToString(",")

            lifecycleScope.launch {
                while (true) {
                    viewModel.onEvent(
                        MapEvent.JourneyPos(
                            llLon = llLon,
                            llLat = llLat,
                            urLon = urLon,
                            urLat = urLat,
                            jid = jid,
                            lines = linesS
                        )
                    )
                    delay(60000) // Actualiza cada 1 min
                }
            }
        }
    }

    private fun centerMapBetweenPoints(startPoint: GeoPoint, endPoint: GeoPoint) {
        // Calcular el centro entre los puntos A y B
        val centerLat = (startPoint.latitude + endPoint.latitude) / 2
        val centerLon = (startPoint.longitude + endPoint.longitude) / 2
        val centerPoint = GeoPoint(centerLat, centerLon)

        // Calcular la distancia entre los puntos A y B
        val distance = startPoint.distanceToAsDouble(endPoint)

        // Ajustar el nivel de zoom en función de la distancia
        val zoomLevel = when {
            distance > 10000 -> 10.0 // Distancia mayor a 10 km
            distance > 5000 -> 12.0  // Distancia mayor a 5 km
            distance > 1000 -> 14.0  // Distancia mayor a 1 km
            else -> 16.0             // Distancia menor a 1 km
        }

        // Centrar el mapa en el punto calculado y ajustar el zoom
        binding.map.controller.apply {
            setZoom(zoomLevel)
            setCenter(centerPoint)
        }
    }

    /**
     * Dibujar polylines que representan la ruta del transporte
     * */
    private fun drawPolylineFromGroup(polylineGroup: PolylineGroup, backgroundColor: String?) {
        val geoPoints = mutableListOf<GeoPoint>()

        polylineGroup.polylineDesc.forEach { polylineDesc ->
            for (i in polylineDesc.crd!!.indices step 2) {
                val lon = polylineDesc.crd[i] // Longitud
                val lat = polylineDesc.crd[i + 1] // Latitud
                geoPoints.add(GeoPoint(lat, lon))
            }

            val polyline = Polyline().apply {
                setPoints(geoPoints)
                outlinePaint.color = backgroundColor?.toColorInt() ?: Color.RED
                outlinePaint.strokeWidth = 10.0f
            }

            binding.map.overlays.add(polyline)
        }
    }

    /**
     * Consulta para obtener ruta de caminata
     * */
    private fun getGisRouteAndDraw(ctx: String) {
        lifecycleScope.launch {
            viewModel.onEvent(MapEvent.RetrieveGisRoute(ctx = ctx))
            viewModel.state.collectLatest { state ->
                when {
                    state.trips.isNotEmpty() -> {
                        val trip = state.trips[0]
                        val leg = trip.legList.leg[0]
                        val backgroundColor = leg.product?.get(0)?.icon?.backgroundColor?.hex
                        if (leg.gisRoute != null) {
                            drawGisRoute(leg.gisRoute, backgroundColor)
                        }
                    }
                }
            }
        }
    }

    /**
     * Dibujar polylines que representan la ruta de caminata
     * */
    private fun drawGisRoute(gisRoute: GisRoute, backgroundColor: String?) {
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
            outlinePaint.color = backgroundColor?.toColorInt() ?: Color.BLUE
            outlinePaint.strokeWidth = 20.0f

            // Crear un Path circular para usarlo como patrón
            val circlePath = Path().apply {
                addCircle(
                    0f,
                    0f,
                    5f,
                    Path.Direction.CW
                ) // Radio de los círculos
            }

            // Aplicar el PathDashPathEffect con separación entre círculos
            outlinePaint.pathEffect = PathDashPathEffect(
                circlePath,
                20f,  // Espaciado entre círculos
                0f,
                PathDashPathEffect.Style.ROTATE
            )
        }
        binding.map.overlays.add(polyline)
    }

    /**
     * Colocar marcador en el mapa
     * */
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

    /**
     * Colocar marcador de las paradas en el mapa
     * */
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
        canvas.drawCircle(
            outerRadius.toFloat(),
            outerRadius.toFloat(),
            outerRadius.toFloat(),
            outerPaint
        )
        // Dibujar el punto blanco en el centro
        canvas.drawCircle(
            outerRadius.toFloat(),
            outerRadius.toFloat(),
            innerRadius.toFloat(),
            innerPaint
        )

        // Asignar el bitmap como icono del marcador
        marker.icon = BitmapDrawable(resources, bitmap)

        binding.map.overlays.add(marker)
    }

    /**
     * Función para obtener los ref de journeyDetail
     * */
    private fun getJourneyRefs(trip: Trip): List<String> {
        val legs = trip.legList.leg

        // Filtrar solo los legs que sean de tipo JNY y extraer JourneyDetailRef.ref
        val journeyRefs = legs
            .filter { leg -> leg.type == "JNY" && leg.journeyDetailRef?.ref != null }
            .map { leg -> leg.journeyDetailRef?.ref!! } // Extraer el valor del campo ref

        return journeyRefs
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
