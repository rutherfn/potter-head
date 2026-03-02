package com.nicholas.rutherford.potter.head.database.converter

import com.nicholas.rutherford.potter.head.database.CharacterFilterType
import com.nicholas.rutherford.potter.head.database.entity.CharacterFilterEntity

/**
 * Converter data class for CharacterFilterEntity.
 * Used to convert between entity and domain model representations.
 *
 * @property id The unique identifier of the character filter.
 * @property filterType The type of the character filter.
 * @property values The list of values associated with the character filter.
 * @property isActive Whether the character filter is active.
 *
 * @author Nicholas Rutherford
 */
data class CharacterFilterConverter(
    val id: Int,
    val filterType: CharacterFilterType,
    val values: List<String>,
    val isActive: Boolean
) {
    /**
     * Converts this converter to a CharacterFilterEntity.
     */
    fun toEntity(): CharacterFilterEntity = CharacterFilterEntity(
        id = id,
        filterType = filterType,
        values = values,
        isActive = isActive
    )

    companion object {
        /**
         * Creates a CharacterFilterConverter from a CharacterFilterEntity.
         */
        fun fromEntity(entity: CharacterFilterEntity): CharacterFilterConverter =
            CharacterFilterConverter(
                id = entity.id,
                filterType = entity.filterType,
                values = entity.values,
                isActive = entity.isActive
            )
    }
}