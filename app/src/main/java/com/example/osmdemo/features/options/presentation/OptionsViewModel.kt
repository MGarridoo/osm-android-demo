package com.example.osmdemo.features.options.presentation

import androidx.lifecycle.ViewModel
import com.example.osmdemo.features.options.domain.AccessType
import com.example.osmdemo.features.options.domain.CyclingDistance
import com.example.osmdemo.features.options.domain.CyclingSpeedType
import com.example.osmdemo.features.options.domain.DrivingSpeedType
import com.example.osmdemo.features.options.domain.TransferType
import com.example.osmdemo.features.options.domain.WalkingDistance
import com.example.osmdemo.features.options.domain.WalkingSpeedType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class OptionsViewModel @Inject constructor(): ViewModel() {

    var state = MutableStateFlow(OptionsState())
        private set

    // Método para actualizar una opción seleccionada
    fun updateOption(sectionTitle: String, optionTitle: String, newValue: Any?) {
        val updatedSections = state.value.sections.map { section ->
            if (section.title == sectionTitle) {
                section.copy(options = section.options.map { option ->
                    if (option.optionTitle == optionTitle) {
                        option.copy(selectedOption = newValue)
                    } else option
                })
            } else section
        }

        val updatedSelectedOptions = state.value.selectedOptions.copy().apply {
            // Actualizar el campo correspondiente en selectedOptions
            updateSelectedOption(sectionTitle, optionTitle, newValue)
        }

        state.value = state.value.copy(
            sections = updatedSections,
            selectedOptions = updatedSelectedOptions
        )
    }

    private fun SelectedOptions.updateSelectedOption(sectionTitle: String, optionTitle: String, newValue: Any?) {
        when (sectionTitle) {
            "Services (Medios de transporte)" -> {
                when (optionTitle) {
                    "Bus" -> isBusSelected = newValue as Boolean
                    "Cable car" -> isCableCarSelected = newValue as Boolean
                }
            }
            "Transfers (Transbordos)" -> {
                when (optionTitle) {
                    "Direct trips only (Sólo enlaces directos)" -> isDirectTripsOnly = newValue as Boolean
                    "Number of transfers (Número de transbordos)" -> transferType = newValue as TransferType
                    "Plan longer transfer times (Tiempo de transbordos más largos)" -> isPlanLongerTransferTimes = newValue as Boolean
                }
            }
            "Walk (Ruta a pie)" -> {
                when (optionTitle) {
                    "Walking distance to stop (Recorrido a pie hasta la parada)" -> walkingDistance = newValue as WalkingDistance
                    "Walking speed (Velocidad a pie)" -> walkingSpeed = newValue as WalkingSpeedType
                }
            }
            "Bike (Bicicleta)" -> {
                when (optionTitle) {
                    "Bicycle transport (Transporte de bicicleta)" -> isBicycleTransport = newValue as Boolean
                    "Cycling distance to stop (Recorrido con bicicleta hasta la parada)" -> cyclingDistance = newValue as CyclingDistance
                    "Cycling speed (Velocidad de bicicleta)" -> cyclingSpeed = newValue as CyclingSpeedType
                }
            }
            "Driving speed (Velocidad con el coche)" -> {
                drivingSpeed = newValue as DrivingSpeedType
            }
            "Travel with disabled access (Opciones de accesibilidad en el trayecto)" -> {
                accessType = newValue as AccessType
            }
        }
    }

}