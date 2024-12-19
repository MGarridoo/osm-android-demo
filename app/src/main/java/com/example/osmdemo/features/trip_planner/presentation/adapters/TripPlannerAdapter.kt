package com.example.osmdemo.features.trip_planner.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.osmdemo.databinding.ItemLocationBinding
import com.example.osmdemo.core.backend.model.StopOrCoordLocation

class TripPlannerAdapter(
    private val onItemClick: (StopOrCoordLocation) -> Unit
) : RecyclerView.Adapter<TripPlannerAdapter.SuggestionViewHolder>() {

    private val locations = mutableListOf<StopOrCoordLocation>()

    fun submitList(newSuggestions: List<StopOrCoordLocation>) {
        locations.clear()
        locations.addAll(newSuggestions)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SuggestionViewHolder {
        val binding = ItemLocationBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SuggestionViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return locations.size
    }

    override fun onBindViewHolder(holder: SuggestionViewHolder, position: Int) {
        holder.bind(locations[position])
    }

    inner class SuggestionViewHolder(private val binding: ItemLocationBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(location: StopOrCoordLocation) {
            when {
                location.stopLocation != null -> {
                    val stopLocation = location.stopLocation
                    binding.textViewSuggestion.text = stopLocation.name
                    // Configura otros campos específicos de StopLocation
                }
                location.coordLocation != null -> {
                    val coordLocation = location.coordLocation
                    binding.textViewSuggestion.text = coordLocation.name
                    // Configura otros campos específicos de CoordLocation
                }
                else -> {
                    //binding.textViewSuggestion.text = "Ubicación desconocida"
                }
            }

            //binding.textViewSuggestion.text = "$location."
            binding.root.setOnClickListener { onItemClick(location) }
        }
    }

}