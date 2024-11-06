package com.example.osmdemo.trips.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.osmdemo.databinding.ItemTripsBinding
import com.example.osmdemo.map.data.model.Trip
import com.example.osmdemo.trips_details.presentation.OnTripClickListener

class TripsAdapter(
    private val trips: List<Trip>,
    private val listener: OnTripClickListener
) : RecyclerView.Adapter<TripsAdapter.TripViewHolder>() {

    inner class TripViewHolder(private val binding: ItemTripsBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(trip: Trip) {
            val bus = trip.legList.leg.find { it.type == "JNY" }
            binding.departureTime.text = "${trip.origin.time}"
            binding.arrivalTime.text = "${trip.destination.time}"
            binding.routeDetail.text = "Salida ${bus?.name}: ${bus?.origin?.time} ${bus?.origin?.name}"
            binding.travelDuration.text = trip.duration
            binding.transferCount.text = "${trip.transferCount ?: 0}"
            binding.root.setOnClickListener { listener.onTripClick(trip) }
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