package com.example.osmdemo.features.trip_search.presentation.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.osmdemo.R
import com.example.osmdemo.core.backend.model.CoordLocation
import com.example.osmdemo.core.backend.model.StopLocation
import com.example.osmdemo.core.backend.model.StopOrCoordLocation
import com.example.osmdemo.databinding.ItemCoordLocationBinding
import com.example.osmdemo.databinding.ItemStopLocationBinding
import com.example.osmdemo.shared.icons.iconMap

class TripSearchAdapter(
    private val onItemClicked: (StopOrCoordLocation) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val items = mutableListOf<StopOrCoordLocation>()

    // Tipos de vista
    companion object {
        private const val VIEW_TYPE_STOP_LOCATION = 1
        private const val VIEW_TYPE_COORD_LOCATION = 2
    }

    override fun getItemViewType(position: Int): Int {
        val item = items[position]
        return when {
            item.stopLocation != null -> VIEW_TYPE_STOP_LOCATION
            item.coordLocation != null -> VIEW_TYPE_COORD_LOCATION
            else -> throw IllegalArgumentException("Invalid item type")
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            VIEW_TYPE_STOP_LOCATION -> {
                val binding = ItemStopLocationBinding.inflate(inflater, parent, false)
                StopLocationViewHolder(binding)
            }
            VIEW_TYPE_COORD_LOCATION -> {
                val binding = ItemCoordLocationBinding.inflate(inflater, parent, false)
                CoordLocationViewHolder(binding)
            }
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = items[position]

        when (holder) {
            is StopLocationViewHolder -> holder.bind(item.stopLocation!!, position, onItemClicked)
            is CoordLocationViewHolder -> holder.bind(item.coordLocation!!, position, onItemClicked)
        }
    }

    override fun getItemCount(): Int = items.size

    fun submitList(newItems: List<StopOrCoordLocation>) {
        items.clear()
        items.addAll(newItems)
        notifyDataSetChanged()
    }

    // ViewHolder para StopLocation
    inner class StopLocationViewHolder(
        private val binding: ItemStopLocationBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(location: StopLocation, position: Int, onClick: (StopOrCoordLocation) -> Unit) {
            Log.d("TAG", "bind: StopLocationViewHolder -> ${location}")

            // Obtener el ícono del mapa basado en el nombre del recurso
            val iconResId = iconMap[location.productAtStop?.get(0)?.icon?.resource] ?: R.drawable.loc_service
            val icon = ContextCompat.getDrawable(binding.root.context, iconResId)

            binding.apply {
                ivIcon.setImageDrawable(icon)
                tvStopName.text = location.name
                tvStopId.text = location.id
                root.setOnClickListener { onClick(StopOrCoordLocation(stopLocation = location)) }
            }
        }
    }

    // ViewHolder para CoordLocation
    inner class CoordLocationViewHolder(
        private val binding: ItemCoordLocationBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(location: CoordLocation, position: Int, onClick: (StopOrCoordLocation) -> Unit) {
            Log.d("TAG", "bind: CoordLocationViewHolder -> $location")

            // Obtener el ícono del mapa basado en el nombre del recurso
            val iconResId = iconMap[location.icon?.resource] ?: R.drawable.loc_service
            val icon = ContextCompat.getDrawable(binding.root.context, iconResId)

            binding.apply {
                ivIcon.setImageDrawable(icon)
                tvCoordName.text = location.name
                tvCoordType.text = location.type
                root.setOnClickListener { onClick(StopOrCoordLocation(coordLocation = location)) }
            }
        }
    }
}
