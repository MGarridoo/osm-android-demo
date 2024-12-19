package com.example.osmdemo.features.trips.presentation

import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.osmdemo.R
import com.example.osmdemo.databinding.ItemTripsBinding
import com.example.osmdemo.core.backend.model.Trip
import com.example.osmdemo.databinding.ItemLegSegmentBinding
import com.example.osmdemo.features.trips_details.presentation.OnTripClickListener
import com.example.osmdemo.shared.extensions.toColorInt
import org.osmdroid.views.overlay.gridlines.LatLonGridlineOverlay.lineColor

class TripsAdapter(
    private val trips: List<Trip>,
    private val listener: OnTripClickListener
) : RecyclerView.Adapter<TripsAdapter.TripViewHolder>() {

    companion object {
        private const val VIEW_TYPE_HEADER = 0
        private const val VIEW_TYPE_TRIP = 1
    }

    inner class TripViewHolder(private val binding: ItemTripsBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(trip: Trip) {
            // Configura los detalles generales del viaje
            val bus = trip.legList.leg.find { it.type == "JNY" }
            binding.departureTime.text = trip.origin.time
            binding.arrivalTime.text = trip.destination.time
            binding.routeDetail.text = "Salida ${bus?.name}: ${bus?.origin?.time} ${bus?.origin?.name}"
            binding.travelDuration.text = trip.duration
            binding.transferCount.text = "${trip.transferCount ?: 0}"
            binding.root.setOnClickListener { listener.onTripClick(trip) }


            // Representación de los segmentos del trayecto
            binding.legContainer.removeAllViews() // Limpia el contenedor antes de agregar nuevos elementos

            trip.legList.leg.forEachIndexed { index, leg ->
                val segmentBinding = ItemLegSegmentBinding.inflate(
                    LayoutInflater.from(binding.root.context),
                    binding.legContainer,
                    false
                )

                // Configura el ícono del tipo de transporte
                segmentBinding.transportIcon.setImageResource(
                    when (leg.type) {
                        "WALK" -> R.drawable.haf_prod_walk
                        "JNY" -> R.drawable.prod_bus
                        else -> R.drawable.prod_bus
                    }
                )

                // Muestra u oculta el TextView según el tipo de transporte
                if (leg.type == "WALK") {
                    segmentBinding.lineName.visibility = View.GONE
                } else {
                    segmentBinding.lineName.visibility = View.VISIBLE
                    segmentBinding.lineName.text = leg.product?.get(0)?.displayNumber ?: ""
                }

                // Configura las líneas
                when {
                    index == 0 -> { // Primer segmento
                        segmentBinding.lineLeft.visibility = View.GONE
                        segmentBinding.lineRight.visibility = View.VISIBLE
                    }
                    index == trip.legList.leg.size - 1 -> { // Último segmento
                        segmentBinding.lineLeft.visibility = View.VISIBLE
                        segmentBinding.lineRight.visibility = View.GONE
                    }
                    else -> { // Segmentos intermedios
                        segmentBinding.lineLeft.visibility = View.VISIBLE
                        segmentBinding.lineRight.visibility = View.VISIBLE
                    }
                }

                // Configura el color de las líneas
                val lineColor = leg.product?.get(0)?.icon?.backgroundColor?.hex?.toColorInt() ?: Color.RED
                segmentBinding.lineLeft.setBackgroundColor(lineColor)
                segmentBinding.lineRight.setBackgroundColor(lineColor)

                // Agrega la vista al contenedor
                binding.legContainer.addView(segmentBinding.root)
            }



        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TripViewHolder {
        val binding = ItemTripsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TripViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TripViewHolder, position: Int) {
        holder.bind(trips[position])
    }

    override fun getItemCount(): Int = trips.size
}