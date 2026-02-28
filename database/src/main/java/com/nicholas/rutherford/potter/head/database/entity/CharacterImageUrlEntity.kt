package com.nicholas.rutherford.potter.head.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Entity representing a Harry Potter character URL the Room database.
 * The character's id we assigned to it via JSON, serves as the primary key.
 *
 * @property id The id of the character.
 * @property characterName The name of the character.
 * @property imageUrl The URL of the character's image.
 *
 * @author Nicholas Rutherford
 */
@Entity(tableName = "characterUrls")
data class CharacterImageUrlEntity(
    @PrimaryKey
    val id: Int,
    val characterName: String,
    val imageUrl: String
)