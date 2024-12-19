package com.example.osmdemo.features.journey_details.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.osmdemo.databinding.ItemStopBinding
import com.example.osmdemo.core.backend.model.Stop

class JourneyDetailsAdapter(
    private val stops: List<Stop>
) : RecyclerView.Adapter<JourneyDetailsAdapter.JourneyDetailsViewHolder>() {

    inner class JourneyDetailsViewHolder(private val binding: ItemStopBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(stop: Stop) {
            val arrTime = stop.arrTime
            val depTime = stop.depTime

            binding.tvArrTime.apply {
                visibility = if (arrTime.isNullOrBlank()) View.GONE else View.VISIBLE
                text = arrTime
            }

            binding.tvDepTime.apply {
                visibility = if (depTime.isNullOrBlank()) View.GONE else View.VISIBLE
                text = depTime
            }

            binding.tvName.text = "${stop.name}"
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JourneyDetailsViewHolder {
        val binding = ItemStopBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return JourneyDetailsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: JourneyDetailsViewHolder, position: Int) {
        holder.bind(stops[position])
    }

    override fun getItemCount(): Int { return stops.size }

}