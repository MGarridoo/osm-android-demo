package com.example.osmdemo.features.options.domain

enum class OptionType { SWITCH, SPINNER }

data class SectionItem(
    val title: String,
    var isExpanded: Boolean = true,
    val options: List<OptionItem>
)

data class OptionItem(
    val optionTitle: String,
    val optionType: OptionType,
    val spinnerOptions: List<Any> = emptyList(),
    var selectedOption: Any? = null
)

/** Cantidad de transbordos */
enum class TransferType(val displayName: String, val maxChange: Int?) {
    UNLIMITED(displayName = "unlimited", maxChange = null), // Sin límites
    NONE(displayName = "none", maxChange = 0), // 0
    MAX_1(displayName = "max. 1x", maxChange = 1), // 1
    MAX_2(displayName = "max. 2x", maxChange = 2), // 2
    MAX_3(displayName = "max. 3x", maxChange = 3); // 3

    override fun toString(): String { return displayName }

    companion object {
        fun fromDisplayName(displayName: String): TransferType? {
            return TransferType.entries.find { it.displayName == displayName }
        }
    }

}

enum class WalkingDistance(val displayName: String, val value: String) {
    WITHOUT(displayName = "without", value = "0"),
    MAX_0_3(displayName = "max. 0.3 mi", value = "500"),
    MAX_0_6(displayName = "max. 0.6 mi", value = "1000"),
    MAX_1_2(displayName = "max. 1.2 mi", value = "2000"),
    MAX_3_1(displayName = "max. 3.1 mi", value = "5000");

    override fun toString(): String { return displayName }

    companion object {
        fun fromDisplayName(displayName: String): WalkingDistance? {
            return WalkingDistance.entries.find { it.displayName == displayName }
        }
    }
}

enum class CyclingDistance(val displayName: String, val value: String) {
    WITHOUT(displayName = "without", value = "0"),
    MAX_0_6(displayName = "max. 0.6 mi", value = "1000"),
    MAX_1_2(displayName = "max. 1.2 mi", value = "2000"),
    MAX_3_1(displayName = "max. 3.1 mi", value = "5000"),
    MAX_6_2(displayName = "max. 6.2 mi", value = "10000"),
    MAX_12_4(displayName = "max. 12.4 mi", value = "20000");

    override fun toString(): String { return displayName }

    companion object {
        fun fromDisplayName(displayName: String): CyclingDistance? {
            return CyclingDistance.entries.find { it.displayName == displayName }
        }
    }

}

enum class AccessType(val displayName: String, val value: String) {
    NON_BARRIER_FREE(displayName = "Non-barrier free", value = "!barrierfree"),
    LIMITED_ACCESS(displayName = "Limited access", value = "limitedBarrierfree"),
    BARRIER_FREE_ACCESS(displayName = "Barrier free access", value = "completeBarrierfree");

    override fun toString(): String {
        return displayName
    }

    companion object {
        fun fromDisplayName(displayName: String): AccessType? {
            return AccessType.entries.find { it.displayName == displayName }
        }

        fun fromApiValue(apiValue: String): AccessType? {
            return AccessType.entries.find { it.value == apiValue }
        }
    }
}

enum class WalkingSpeedType(val displayName: String, val value: String) {
    SLOW(displayName = "Slow", value = "200"),
    NORMAL(displayName = "Normal", value = "100"),
    FAST(displayName = "Fast", value = "50");

    override fun toString(): String { return displayName }

    companion object {
        fun fromDisplayName(displayName: String): WalkingSpeedType? {
            return WalkingSpeedType.entries.find { it.displayName == displayName }
        }
    }
}

enum class CyclingSpeedType(val displayName: String, val value: String) {
    SLOW(displayName = "Slow", value = "150"),
    NORMAL(displayName = "Normal", value = "100"),
    FAST(displayName = "Fast", value = "50");

    override fun toString(): String { return displayName }

    companion object {
        fun fromDisplayName(displayName: String): CyclingSpeedType? {
            return CyclingSpeedType.entries.find { it.displayName == displayName }
        }
    }
}


enum class DrivingSpeedType(val displayName: String, val value: String) {
    SLOW(displayName = "Slow", value = "150"),
    NORMAL(displayName = "Normal", value = "100"),
    FAST(displayName = "Fast", value = "50");

    override fun toString(): String { return displayName }

    companion object {
        fun fromDisplayName(displayName: String): DrivingSpeedType? {
            return entries.find { it.displayName == displayName }
        }
    }
}




