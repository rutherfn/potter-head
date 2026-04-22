package com.nicholas.rutherford.potter.head.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.nicholas.rutherford.potter.head.database.CharacterFilterType

/**
 * Entity representing a character filter in the Room database.
 * The id that automatically generates serves as the primary key.
 *
 * @property id The id of the filter.
 * @property filterType The type of the filter.
 * @property values The values of the filter.
 * @property isActive Whether the filter is active.
 *
 * @author Nicholas Rutherford
 */
@Entity(tableName = "characterFilters")
data class CharacterFilterEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val filterType: CharacterFilterType,
    val values: List<String>,
    val isActive: Boolean
)
