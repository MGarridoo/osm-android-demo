package com.example.osmdemo.trips_details.presentation.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.osmdemo.databinding.ItemJourneyBinding
import com.example.osmdemo.map.data.model.Leg
import com.example.osmdemo.map.data.model.Trip
import com.example.osmdemo.trips.presentation.OnLegClickListener

class TripsDetailsAdapter(
    private val legs: List<Leg>,
    private val gis: List<Trip>,
    private val listener: OnLegClickListener,
) : RecyclerView.Adapter<TripsDetailsAdapter.JourneyViewHolder>() {

    private val legToGisIndexMap = mutableMapOf<Int, Int>()

    init {
        var gisIndex = 0
        legs.forEachIndexed { index, leg ->
            if (leg.type == "WALK") {
                legToGisIndexMap[index] = gisIndex
                gisIndex++
            }
        }
    }

    inner class JourneyViewHolder(private val binding: ItemJourneyBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(leg: Leg, position: Int, context: Context) {

            binding.tvOriginTime.text = "${leg.origin.time}"
            binding.tvOrigin.text = leg.origin.name

            when(leg.type) {
                "WALK" -> {
                    binding.busDetailItem.root.visibility = View.GONE
                    binding.walkDetailItem.root.visibility = View.VISIBLE
                    binding.walkDetailItem.tvName.text = "${leg.name}: ${leg.dist} (${leg.duration})"

                    val gisIndex = legToGisIndexMap[position]
                    var isExpanded = false

                    if (gisIndex != null && gisIndex < gis.size) {
                        val currentGis = gis[gisIndex]

                        with(binding.walkDetailItem.recyclerDetails) {
                            layoutManager = LinearLayoutManager(context)
                            adapter = ItemSegmentAdapter(currentGis.legList.leg.first().gisRoute?.segments)
                            visibility = if (isExpanded) View.VISIBLE else View.GONE
                        }

                        binding.walkDetailItem.tvExpandCollapse.setOnClickListener {
                            isExpanded = !isExpanded
                            binding.walkDetailItem.recyclerDetails.visibility = if (isExpanded) View.VISIBLE else View.GONE
                            binding.walkDetailItem.tvExpandCollapse.text = if (isExpanded) "Ocultar detalles" else "Ver detalles"
                        }

                    }
                }

                "JNY" -> {
                    var isExpanded = false

                    binding.walkDetailItem.root.visibility = View.GONE
                    binding.busDetailItem.root.visibility = View.VISIBLE
                    binding.busDetailItem.tvName.text = "${leg.name}"
                    binding.busDetailItem.tvStops.text = "${leg.duration}, ${leg.stops?.stop?.size} Paradas intermedias"

                    with(binding.busDetailItem.recyclerDetails) {
                        layoutManager = LinearLayoutManager(context)
                        adapter = leg.stops?.let { ItemStopAdapter(it.stop) }
                        visibility = if (isExpanded) View.VISIBLE else View.GONE
                    }

                    binding.busDetailItem.tvName.setOnClickListener { listener.onLegClick(leg) }

                    binding.busDetailItem.tvExpandCollapse.setOnClickListener {
                        isExpanded = !isExpanded
                        binding.busDetailItem.recyclerDetails.visibility = if (isExpanded) View.VISIBLE else View.GONE
                        binding.busDetailItem.tvExpandCollapse.text = if (isExpanded) "Ocultar detalles" else "Ver detalles"
                    }
                }
            }

            // Esta es la última posición
            if (position == legs.size - 1) {
                binding.decoration.visibility = View.VISIBLE
                binding.linearDestination.visibility = View.VISIBLE
                binding.tvDestinationTime.text = "${leg.destination.time}"
                binding.tvDestination.text = leg.destination.name
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JourneyViewHolder {
        val binding = ItemJourneyBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return JourneyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: JourneyViewHolder, position: Int) {
        val context = holder.itemView.context
        holder.bind(legs[position], position, context)
    }

    override fun getItemCount(): Int { return legs.size }

}