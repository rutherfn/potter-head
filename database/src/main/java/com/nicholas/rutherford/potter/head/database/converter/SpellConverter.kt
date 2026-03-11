package com.nicholas.rutherford.potter.head.database.converter

import com.nicholas.rutherford.potter.head.database.entity.SpellEntity
import com.nicholas.rutherford.potter.head.model.network.SpellResponse

/**
 * Spell data class for SpellEntity.
 * Used to convert between entity and domain model representations.
 *
 * @property name The name of the spell.
 * @property description The description of the spell.
 *
 * @author Nicholas Rutherford
 */
data class SpellConverter(
    val name: String,
    val description: String
) {

    /**
     * Converts this converter to a SpellEntity.
     */
    fun toEntity(): SpellEntity = SpellEntity(
        name = name,
        description = description
    )

    companion object {

        /**
         * Creates a SpellConverter from a SpellEntity.
         */
        fun fromEntity(entity: SpellEntity): SpellConverter = SpellConverter(
            name = entity.name,
            description = entity.description
        )

        /**
         * Creates a SpellConverter from a SpellResponse.
         */
        fun fromResponse(response: SpellResponse): SpellConverter = SpellConverter(
            name = response.name,
            description = response.description
        )
    }
}