package com.example.osmdemo.trips_details.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.osmdemo.R
import com.example.osmdemo.databinding.ItemSegmentBinding
import com.example.osmdemo.map.data.model.Segment

class ItemSegmentAdapter(
    private val segments: List<Segment>?
) : RecyclerView.Adapter<ItemSegmentAdapter.SegmentViewHolder>() {

    inner class SegmentViewHolder(private val binding: ItemSegmentBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(segment: Segment) {
            binding.tvInstruction.text = "${segment.manTx}"
            binding.tvDistance.text = "${segment.dist} m"

            // Asignar icono según la instrucción
            val iconResId = when (segment.man) {
                "LE" -> R.drawable.haf_navi_left // Ícono para girar a la izquierda
                "RI" -> R.drawable.haf_navi_right // Ícono para girar a la derecha
                //"FR" -> R.drawable.ic_go_straight // Ícono para seguir recto
                //"ST" -> R.drawable.ic_start // Ícono para el inicio del recorrido
                //"AR" -> R.drawable.ic_arrival // Ícono para llegada
                else -> null // Ícono por defecto
            }

            if (iconResId != null) { binding.directionIcon.setImageResource(iconResId) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SegmentViewHolder {
        val binding = ItemSegmentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SegmentViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SegmentViewHolder, position: Int) {
        val item = segments?.get(position)
        if (item != null) { holder.bind(item) }
    }

    override fun getItemCount(): Int {
        return segments?.size ?: 0
    }

}