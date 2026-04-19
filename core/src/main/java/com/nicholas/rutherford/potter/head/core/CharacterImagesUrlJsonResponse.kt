package com.nicholas.rutherford.potter.head.core

/**
 * Data class representing the JSON response for character image URLs.
 *
 * @param id The unique identifier of the character.
 * @param characterName The name of the character.
 * @param imageUrl The URL of the character's image.
 *
 * @author Nicholas Rutherford
 */
data class CharacterImagesUrlJsonResponse(
    val id: Int,
    val characterName: String,
    val imageUrl: String
)
