package com.nicholas.rutherford.potter.head.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Entity representing a spell from Harry Potter in the Room database.
 * The spell name serves as the primary key.
 *
 * @property name The name of the spell.
 * @property description the description of the spell
 *
 * @author Nicholas Rutherford
 */
@Entity(tableName = "spells")
data class SpellEntity(
    @PrimaryKey
    val name: String,
    val description: String
)
