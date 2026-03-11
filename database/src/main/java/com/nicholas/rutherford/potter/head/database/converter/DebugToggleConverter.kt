package com.nicholas.rutherford.potter.head.database.converter

import com.nicholas.rutherford.potter.head.database.entity.DebugToggleEntity

/**
 * Converter data class for DebugToggleEntity.
 * Used to convert between entity and domain model representations.
 *
 * @property toggleKey Unique identifier for the toggle.
 * @property isEnabled Whether the toggle is currently enabled.
 *
 * @author Nicholas Rutherford
 */
data class DebugToggleConverter(
    val toggleKey: String,
    val isEnabled: Boolean
) {

    /**
     * Converts this converter to a DebugToggleEntity.
     */
    fun toEntity(): DebugToggleEntity = DebugToggleEntity(
        toggleKey = toggleKey,
        isEnabled = isEnabled
    )

    companion object {

        /**
         * Creates a DebugToggleConverter from a DebugToggleEntity.
         */
        fun fromEntity(entity: DebugToggleEntity): DebugToggleConverter = DebugToggleConverter(
            toggleKey = entity.toggleKey,
            isEnabled = entity.isEnabled
        )
    }
}


