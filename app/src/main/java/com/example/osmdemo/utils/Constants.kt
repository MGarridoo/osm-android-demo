package com.example.osmdemo.utils

import com.example.osmdemo.features.options.domain.AccessType
import com.example.osmdemo.features.options.domain.CyclingDistance
import com.example.osmdemo.features.options.domain.CyclingSpeedType
import com.example.osmdemo.features.options.domain.OptionItem
import com.example.osmdemo.features.options.domain.OptionType
import com.example.osmdemo.features.options.domain.SectionItem
import com.example.osmdemo.features.options.domain.DrivingSpeedType
import com.example.osmdemo.features.options.domain.TransferType
import com.example.osmdemo.features.options.domain.WalkingDistance
import com.example.osmdemo.features.options.domain.WalkingSpeedType

val menuOptions = mutableListOf(
    SectionItem(
        title = "Services (Medios de transporte)",
        options = mutableListOf(
            OptionItem(
                optionTitle = "Bus",
                optionType = OptionType.SWITCH,
                selectedOption = true
            ),
            OptionItem(
                optionTitle = "Cable car",
                optionType = OptionType.SWITCH,
                selectedOption = true
            ),
        )
    ),
    SectionItem(
        title = "Transfers (Transbordos)",
        options = mutableListOf(
            OptionItem(
                optionTitle = "Direct trips only (Sólo enlaces directos)",
                optionType = OptionType.SWITCH,
                selectedOption = false
            ),
            OptionItem(
                optionTitle = "Number of transfers (Número de transbordos)",
                optionType = OptionType.SPINNER,
                spinnerOptions = listOf(
                    TransferType.UNLIMITED,
                    TransferType.NONE,
                    TransferType.MAX_1,
                    TransferType.MAX_2,
                    TransferType.MAX_3
                ),
                selectedOption = TransferType.UNLIMITED
            ),
            OptionItem(
                optionTitle = "Plan longer transfer times (Tiempo de transbordos más largos)",
                optionType = OptionType.SWITCH,
                selectedOption = true
            )
        )
    ),
    SectionItem(
        title = "Walk (Ruta a pie)",
        options = mutableListOf(
            OptionItem(
                optionTitle = "Walking distance to stop (Recorrido a pie hasta la parada)",
                optionType = OptionType.SPINNER,
                spinnerOptions = listOf(
                    WalkingDistance.WITHOUT,
                    WalkingDistance.MAX_0_3,
                    WalkingDistance.MAX_0_6,
                    WalkingDistance.MAX_1_2,
                    WalkingDistance.MAX_3_1
                ),
                selectedOption = WalkingDistance.MAX_1_2
            ),
            OptionItem(
                optionTitle = "Walking speed (Velocidad a pie)",
                optionType = OptionType.SPINNER,
                spinnerOptions = listOf(
                    WalkingSpeedType.SLOW,
                    WalkingSpeedType.NORMAL,
                    WalkingSpeedType.FAST
                ),
                selectedOption = WalkingSpeedType.NORMAL
            ),
        )
    ),
    SectionItem(
        title = "Bike (Bicicleta)",
        options = mutableListOf(
            OptionItem(
                optionTitle = "Bicycle transport (Transporte de bicicleta)",
                optionType = OptionType.SWITCH,
                selectedOption = false
            ),
            OptionItem(
                optionTitle = "Cycling distance to stop (Recorrido con bicicleta hasta la parada)",
                optionType = OptionType.SPINNER,
                spinnerOptions = listOf(
                    CyclingDistance.WITHOUT,
                    CyclingDistance.MAX_0_6,
                    CyclingDistance.MAX_1_2,
                    CyclingDistance.MAX_3_1,
                    CyclingDistance.MAX_6_2,
                    CyclingDistance.MAX_12_4
                ),
                selectedOption = CyclingDistance.WITHOUT
            ),
            OptionItem(
                optionTitle = "Cycling speed (Velocidad de bicicleta)",
                optionType = OptionType.SPINNER,
                spinnerOptions = listOf(
                    CyclingSpeedType.SLOW,
                    CyclingSpeedType.NORMAL,
                    CyclingSpeedType.FAST
                ),
                selectedOption = CyclingSpeedType.NORMAL
            ),
        )
    ),
    SectionItem(
        title = "Driving speed (Velocidad con el coche)",
        options = mutableListOf(
            OptionItem(
                optionTitle = "Driving speed (Velocidad con el coche)",
                optionType = OptionType.SPINNER,
                spinnerOptions = listOf(DrivingSpeedType.SLOW, DrivingSpeedType.NORMAL, DrivingSpeedType.FAST),
                selectedOption = DrivingSpeedType.NORMAL
            ),
        )
    ),
    SectionItem(
        title = "Travel with disabled access (Opciones de accesibilidad en el trayecto)",
        options = mutableListOf(
            OptionItem(
                optionTitle = "Travel with disabled access (Opciones de accesibilidad en el trayecto)",
                optionType = OptionType.SPINNER,
                spinnerOptions = listOf(
                    AccessType.BARRIER_FREE_ACCESS,
                    AccessType.LIMITED_ACCESS,
                    AccessType.NON_BARRIER_FREE
                ),
                selectedOption = AccessType.NON_BARRIER_FREE
            ),
        )
    )
)
