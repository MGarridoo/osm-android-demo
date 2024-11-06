package com.example.osmdemo.trips_details.presentation.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.osmdemo.databinding.ItemStopBinding
import com.example.osmdemo.map.data.model.Stop

class ItemStopAdapter(
    private val stops: List<Stop>
) : RecyclerView.Adapter<ItemStopAdapter.BusDetailViewHolder>() {

    inner class BusDetailViewHolder(private val binding: ItemStopBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(stop: Stop) {

            binding.tvArrTime.apply {
                text = stop.arrTime ?: ""
                visibility = if (stop.arrTime != null) View.VISIBLE else View.GONE
            }

            binding.tvDepTime.apply {
                text = stop.depTime ?: ""
                visibility = if (stop.depTime != null) View.VISIBLE else View.GONE
            }

            binding.tvName.text = stop.name
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BusDetailViewHolder {
        val binding = ItemStopBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BusDetailViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BusDetailViewHolder, position: Int) {
        holder.bind(stops[position])
    }

    override fun getItemCount(): Int { return stops.size }

}