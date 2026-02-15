package com.nicholas.rutherford.potter.head.database.entity

/**
 * Data class representing a wand entity.
 * This is stored as JSON in the CharacterEntity via a TypeConverter,
 * not as a separate database table.
 *
 * @property core The core material of the wand.
 * @property wood The wood type of the wand, nullable.
 * @property length The length of the wand.
 *
 * @author Nicholas Rutherford
 */
data class WandEntity(
    val core: String,
    val wood: String?,
    val length: Double
)