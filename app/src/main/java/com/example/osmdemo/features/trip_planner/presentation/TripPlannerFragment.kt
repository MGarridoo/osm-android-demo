package com.example.osmdemo.features.trip_planner.presentation

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.osmdemo.R
import com.example.osmdemo.core.backend.model.Journey
import com.example.osmdemo.core.backend.model.PolylineGroup
import com.example.osmdemo.core.backend.model.StopOrCoordLocation
import com.example.osmdemo.databinding.FragmentTripPlannerBinding
import com.example.osmdemo.shared.FragmentDateTimePicker
import com.example.osmdemo.shared.extensions.createResizedDrawable
import com.example.osmdemo.utils.decodePolyline
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.osmdroid.events.MapListener
import org.osmdroid.events.ScrollEvent
import org.osmdroid.events.ZoomEvent
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.overlay.Marker

@AndroidEntryPoint
class TripPlannerFragment : Fragment() {

    private var _binding: FragmentTripPlannerBinding? = null
    private val binding get() = _binding!!

    private var debounceJob: Job? = null
    private var isMapExpanded = false

    private val busMarkers = mutableMapOf<String, Marker>() // Almacena todos los marcadores por bus

    private val viewModel: TripPlannerViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTripPlannerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Configuración del mapa
        setupOnMap()

        // Quitar foco inicial y retrasar la configuración del listener
        view.post {
            //binding.searchStartInput.clearFocus()
            //binding.searchDestinationInput.clearFocus()
            //binding.root.requestFocus()

            // Configura listeners de los campos de texto después de quitar el foco
            binding.searchStartInput.onFocusChangeListener = onFocusChangeListener()
            binding.searchDestinationInput.onFocusChangeListener = onFocusChangeListener()
        }

        // Escuchar los estados del state
        onStateListener()

        // Escuchar resultados del TripSearchFragment
        onResultListener()

        // Configura listeners de los campos de texto
        //binding.searchStartInput.onFocusChangeListener = onFocusChangeListener()
        //binding.searchDestinationInput.onFocusChangeListener = onFocusChangeListener()

        // Configura acciones en botones
        onClickTime()
        onClickOptions()
        onSearchButton()
    }

    private fun setupMapListeners() {
        // Refrescar el mapa
        binding.map.invalidate()

        // Obtener los cuadrantes
        binding.map.addMapListener(object : MapListener {
            override fun onScroll(event: ScrollEvent?): Boolean {
                debounceUpdateBoundingBox()
                return true
            }

            override fun onZoom(event: ZoomEvent?): Boolean {
                debounceUpdateBoundingBox()
                return true
            }
        })
    }

    private fun debounceUpdateBoundingBox() {
        // Cancelar cualquier trabajo pendiente
        debounceJob?.cancel()

        // Crear un nuevo trabajo con un retraso para agrupar eventos
        debounceJob = lifecycleScope.launch {
            delay(1000) // Ajusta el tiempo de debounce (en milisegundos)
            binding.map.overlays.clear()
            binding.map.invalidate()
            updateBoundingBox()
        }
    }

    /**
     * Configuración de mapa
     * */
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

        // Agregar un MapListener para esperar el evento de cambio de mapa
        setupMapListeners()
    }

    private fun updateBoundingBox() {
        val boundingBox = binding.map.boundingBox
        if (boundingBox != null) {
            val llLon = boundingBox.lonWest  // Longitud inferior izquierda
            val llLat = boundingBox.latSouth // Latitud inferior izquierda
            val urLon = boundingBox.lonEast  // Longitud superior derecha
            val urLat = boundingBox.latNorth // Latitud superior derecha

            Log.d("TAG", "updateBoundingBox: llLon -- > $llLon")
            Log.d("TAG", "updateBoundingBox: llLat -- > $llLat")
            Log.d("TAG", "updateBoundingBox: urLon -- > $urLon")
            Log.d("TAG", "updateBoundingBox: urLat -- > $urLat")

            /*viewModel.onJourneyPos(
                llLon = llLon,
                llLat = llLat,
                urLon = urLon,
                urLat = urLat,
                jid = null,
                date = null,
                time = null
            )*/
        }
    }

    private fun drawPolylinesOnMap(polylineGroups: List<PolylineGroup?>) {
        // Asegúrate de que haya al menos un polylineGroup
        if (polylineGroups.isEmpty()) return

        polylineGroups.forEach { polylineGroup ->
            polylineGroup?.polylineDesc?.forEach { polylineDesc ->
                // Asegúrate de que las coordenadas existan
                val coordinates = polylineDesc.crd ?: return

                // Crear una lista de GeoPoints a partir de las coordenadas
                val geoPoints = mutableListOf<GeoPoint>()
                for (i in coordinates.indices step 2) {
                    val lon = coordinates[i] // Longitud
                    val lat = coordinates[i + 1] // Latitud
                    geoPoints.add(GeoPoint(lat, lon))
                }

                // Simular el recorrido con un marcador
                animateBusMarker(geoPoints)
            }
        }
    }

    private fun addBusMarker(position: GeoPoint) {
        val resizedDrawable =
            createResizedDrawable(
                context = requireContext(),
                drawableId =  R.drawable.prod_bus,
                scaleFactor = 0.3f // Escala al 30%
            )

        val busMarker = Marker(binding.map).apply {
            icon = resizedDrawable // Asignar el drawable redimensionado
            setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
            this.position = position // Posición del marcador
        }

        binding.map.overlays.add(busMarker)
        binding.map.invalidate()
    }

    private fun animateBusMarker(geoPoints: List<GeoPoint>) {
        if (geoPoints.isEmpty()) return

        lifecycleScope.launch {
            val resizedDrawable = createResizedDrawable(
                context = requireContext(),
                drawableId = R.drawable.prod_bus,
                scaleFactor = 0.3f
            )

            val busMarker = Marker(binding.map).apply {
                icon = resizedDrawable
                setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
                position = geoPoints.first() // Iniciar en el primer punto
            }

            binding.map.overlays.add(busMarker)
            binding.map.invalidate()

            for (i in 0 until geoPoints.size - 1) {
                val start = geoPoints[i]
                val end = geoPoints[i + 1]

                val distance = start.distanceToAsDouble(end)
                val transitionDuration = (distance * 20).toLong().coerceIn(1000, 5000)
                val steps = 100
                val delayBetweenSteps = transitionDuration / steps

                for (step in 1..steps) {
                    val fraction = step / steps.toFloat()
                    val interpolatedLat = start.latitude + fraction * (end.latitude - start.latitude)
                    val interpolatedLon = start.longitude + fraction * (end.longitude - start.longitude)

                    // Verifica si el binding sigue activo antes de actualizar el marcador
                    if (_binding != null) {
                        busMarker.position = GeoPoint(interpolatedLat, interpolatedLon)
                        binding.map.invalidate()
                    } else {
                        // Si el binding es nulo, detén la animación
                        return@launch
                    }

                    delay(delayBetweenSteps)
                }
            }

            if (_binding != null) {
                binding.map.overlays.remove(busMarker)
                binding.map.invalidate()
            }
        }
    }

    /**
     * Escuchar los cambios de fechas, origen, destino, entre otros...
     * */
    private fun onStateListener() {
        lifecycleScope.launch {
            if (_binding != null) {
                viewModel.state.collectLatest { state ->
                    val type = state.isDeparture
                    val date = state.displayTime
                    val startLocation = state.startLocation?.stopLocation?.name ?: state.startLocation?.coordLocation?.name ?: ""
                    val destinationLocation = state.destinationLocation?.stopLocation?.name ?: state.destinationLocation?.coordLocation?.name ?: ""

                    binding.searchStartInput.setText(startLocation)
                    binding.searchDestinationInput.setText(destinationLocation)
                    binding.type.text = if (type) "Hora de salida: " else "Hora de llegada: "
                    binding.date.text = "$date"

                    if (state.isLoading) {
                        binding.progressBar.visibility = View.VISIBLE
                        //binding.map.visibility = View.GONE
                    } else {
                        binding.progressBar.visibility = View.GONE
                        // Una vez que isLoading es false, podemos usar polylineGroups
                        val journeys = state.journeys
                        binding.map.invalidate()
                        updateBusMarkers(journeys)

                        val polylineGroups = state.polylineGroups
                        drawPolylinesOnMap(polylineGroups)
                    }

                    // Mostrar mensajes de error
                    state.errorMessage?.let {
                        Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
                    }

                }
            }
        }
    }

    /**
     * Obtener resultado de la ubicación seleccionado
     * */
    private fun onResultListener() {
        parentFragmentManager.setFragmentResultListener("tripSearchResult", viewLifecycleOwner) { _, bundle ->
            val selectedLocation = bundle.getParcelable<StopOrCoordLocation>("selectedLocation")
            val isStartInput = bundle.getBoolean("isStartInput")

            selectedLocation?.let {
                val locationType = if (isStartInput) LOCATION.A else LOCATION.B
                viewModel.onEvent(TripPlannerEvent.OnLocationSelected(it, locationType))
            }
        }
    }

    /**
     * Obtener focus del buscador seleccionado
     * */
    private fun onFocusChangeListener(): View.OnFocusChangeListener {
        return View.OnFocusChangeListener { v, hasFocus ->
            if (hasFocus) {
                // Determina cuál campo tiene el foco
                val isStartInput = v.id == binding.searchStartInput.id
                val state = viewModel.state.value

                val currentText = if (isStartInput) {
                    state.startLocation?.stopLocation?.name ?: state.startLocation?.coordLocation?.name ?: ""
                } else {
                    state.destinationLocation?.stopLocation?.name ?: state.destinationLocation?.coordLocation?.name ?: ""
                }

                // Navega al TripSearchFragment enviando el texto actual
                findNavController().navigate(
                    TripPlannerFragmentDirections.actionTripPlannerFragmentToTripSearchFragment(
                        isStartInput = isStartInput,
                        currentText = currentText
                    )
                )
            }
        }
    }

    /**
     * Click para seleccionar fecha y hora de salida o de llegada
     * */
    private fun onClickTime() {
        binding.date.setOnClickListener {
            val dateTimePicker = FragmentDateTimePicker()
            dateTimePicker.show(parentFragmentManager, "DateTimePicker")
        }
    }

    /**
     * Click para seleccionar las opciones del viajes (límite de velocidades, transbordos, entre otros...)
     * */
    private fun onClickOptions() {
        binding.optionsButton.setOnClickListener {
            findNavController().navigate(
                TripPlannerFragmentDirections.actionTripPlannerFragmentToOptionsFragment()
            )
        }
    }

    /**
     * Click para buscar los viajes de acuerdo a origen A y B
     * */
    private fun onSearchButton() {
        binding.searchButton.setOnClickListener {
            val state = viewModel.state.value

            /*val originCoordLong = state.startLocation?.stopLocation?.lon
                ?: state.startLocation?.coordLocation?.lon

            val originCoordLat = state.startLocation?.stopLocation?.lat
                ?: state.startLocation?.coordLocation?.lat

            val destCoordLong = state.destinationLocation?.stopLocation?.lon
                ?: state.destinationLocation?.coordLocation?.lon

            val destCoordLat = state.destinationLocation?.stopLocation?.lat
                ?: state.destinationLocation?.coordLocation?.lat*/


            val originCoordLong = -74.119018
            val originCoordLat = 4.687623
            val destCoordLong = -74.06546
            val destCoordLat = 4.643881

            /*val originCoordLong = -74.1154376653001
            val originCoordLat = 4.514272472695012
            val destCoordLong = -74.09342259953381
            val destCoordLat = 4.754731941463538*/

            val date = state.date
            val time = state.time
            val searchForArrival = if (state.isDeparture) 0 else 1

            Log.d("TAG", "onSearchButton: originCoordLong -> $originCoordLong")
            Log.d("TAG", "onSearchButton: originCoordLat -> $originCoordLat")
            Log.d("TAG", "onSearchButton: destCoordLong -> $destCoordLong")
            Log.d("TAG", "onSearchButton: destCoordLat -> $destCoordLat")

            Log.d("TAG", "onSearchButton: state -> $state")
            Log.d("TAG", "onSearchButton: date -> $date")
            Log.d("TAG", "onSearchButton: time -> $time")
            Log.d("TAG", "onSearchButton: searchForArrival -> $searchForArrival")

            if (state.startLocation == null || state.destinationLocation == null) {
                Toast.makeText(
                    requireContext(),
                    "Por favor selecciona ambas ubicaciones.",
                    Toast.LENGTH_SHORT
                ).show()
            }

            if (originCoordLong != null && originCoordLat != null && destCoordLong != null && destCoordLat != null) {
                val action = TripPlannerFragmentDirections.actionTripPlannerFragmentToTripsFragment(
                    originCoordLong = "$originCoordLong",
                    originCoordLat = "$originCoordLat",
                    destCoordLong = "$destCoordLong",
                    destCoordLat = "$destCoordLat",
                    date = date,
                    time = time,
                    searchForArrival = searchForArrival
                )
                findNavController().navigate(action)
            } else {
                Toast.makeText(context, "No se pudieron obtener los IDs de las ubicaciones.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun updateBusMarkers(journeys: List<Journey>) {
        journeys.forEach { journey ->
            val bus = journey.name ?: "Bus"
            val polylineGroup = journey.journeyPath?.polylineGroup
            val crdEncYX = polylineGroup?.polylineDesc?.firstOrNull()?.crdEncYX

            if (polylineGroup != null && crdEncYX != null) {
                //drawBusRoute(polylineGroup.polylineDesc)
                val decodedPoints = decodePolyline(crdEncYX)
                simulateBusMovement(decodedPoints, bus)
            }

            Log.d("TAG", "updateBusMarkers: journey -> $journey")
        }
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
                position = geoPoints.first()
                title = bus
                icon = iconBus
                binding.map.overlays.add(this) // Añadir el marcador al mapa
            }
        }

        // Lanzar una corrutina que actualice la posición de este bus
        lifecycleScope.launch {
            for (point in geoPoints) {
                delay(10000) // Intervalo de tiempo entre puntos (simulación)

                // Verificar que el binding y el mapa no sean null antes de continuar
                if (binding != null) {
                    // Mover el marcador del bus
                    currentBusMarker.position = point
                    binding.map.invalidate()
                } else {
                    Log.w("MapUpdate", "El binding o el mapa no están disponibles")
                    break
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        debounceJob?.cancel()
        _binding = null
    }

}
