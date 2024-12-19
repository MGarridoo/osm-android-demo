package com.example.osmdemo.features.options.presentation

import com.example.osmdemo.features.options.domain.AccessType
import com.example.osmdemo.features.options.domain.CyclingDistance
import com.example.osmdemo.features.options.domain.CyclingSpeedType
import com.example.osmdemo.features.options.domain.SectionItem
import com.example.osmdemo.features.options.domain.DrivingSpeedType
import com.example.osmdemo.features.options.domain.TransferType
import com.example.osmdemo.features.options.domain.WalkingDistance
import com.example.osmdemo.features.options.domain.WalkingSpeedType
import com.example.osmdemo.utils.menuOptions

data class OptionsState(
    val sections: List<SectionItem> = menuOptions,
    val selectedOptions: SelectedOptions = SelectedOptions()
)

data class SelectedOptions(
    // Services (Servicios)
    var isBusSelected: Boolean = true,
    var isCableCarSelected: Boolean = true,

    // Transfers (Transbordos)
    var isDirectTripsOnly: Boolean = false,
    var transferType: TransferType = TransferType.UNLIMITED,
    var isPlanLongerTransferTimes: Boolean = true,

    // Walk (Caminatas)
    var walkingDistance: WalkingDistance = WalkingDistance.MAX_1_2,
    var walkingSpeed: WalkingSpeedType = WalkingSpeedType.NORMAL,

    // Bike (Bicicleta)
    var isBicycleTransport: Boolean = false,
    var cyclingDistance: CyclingDistance = CyclingDistance.WITHOUT,
    var cyclingSpeed: CyclingSpeedType = CyclingSpeedType.NORMAL,

    // Driving (Velocidad de vehiculo)
    var drivingSpeed: DrivingSpeedType = DrivingSpeedType.NORMAL,

    // Accessibility (Accesibilidad)
    var accessType: AccessType = AccessType.NON_BARRIER_FREE
) {

    /**
     * Se utiliza para saber si el transporte en bicicleta está habilitado o no
     * */
    private val bicycleTransport: Int get() = if (isBicycleTransport) 1 else 0

    /**
     * Se utiliza para saber la cantidad total de transferencias o transbordos
     * */
    val maxChanges: Int? get() {
        val tripsOnly = isDirectTripsOnly
        return if (tripsOnly) 0 else transferType.maxChange
    }

    /**
     * Se utiliza para calcular el valor del transporte o productos (Cable, Bus, ...)
     * */
    val products: Int get() {
        var transportValue = 0

        if (isBusSelected) { transportValue += 256 }

        if (isCableCarSelected) { transportValue += 2048 }

        return transportValue
    }

    /**
     * Se utiliza para obtener el total de caminata (activación, distancia min., distancia max., velocidad...)
     * */
    val totalWalk: String get() {
        val enabled = "1"
        val minDistance = "0"
        val maxDistance = walkingDistance.value
        val speed = walkingSpeed.value

        return "$enabled,$minDistance,$maxDistance,$speed"
    }

    /**
     * Se utiliza para obtener el total de bicicleta (activación, distancia min., distancia max., velocidad...)
     * */
    val totalBike: String get() {
        val enabled = bicycleTransport
        val minDistance = "0"
        val maxDistance = cyclingDistance.value
        val speed = cyclingSpeed.value

        return "$enabled,$minDistance,$maxDistance,$speed"
    }

    /**
     * Se utiliza para obtener el total de manejo (activación, distancia min., distancia max., velocidad...)
     * */
    val totalCar: String get() {
        val enabled = "1"
        val minDistance = "0"
        val maxDistance = "2000"
        val speed = drivingSpeed.value

        return "$enabled,$minDistance,$maxDistance,$speed"
    }

    /**
     * Mapea el perfil de tiempo de transbordos a un valor numérico.
     */
    val changeTimePercent: Int
        get() = if (isPlanLongerTransferTimes) 100 else 150

    /**
     * Agregar tiempo extra en los transbordos.
     */
    val addChangeTime: Int?
        get() = if (isPlanLongerTransferTimes) 4 else null


}