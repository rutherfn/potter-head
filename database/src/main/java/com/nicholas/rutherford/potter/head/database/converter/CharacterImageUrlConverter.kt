package com.nicholas.rutherford.potter.head.database.converter

import com.nicholas.rutherford.potter.head.core.CharacterImagesUrlJsonResponse
import com.nicholas.rutherford.potter.head.database.entity.CharacterImageUrlEntity

/**
 * Converter data class for CharacterImageUrlEntity.
 * Used to convert between entity and domain model representations.
 *
 * @property id The id of the character.
 * @property characterName The name of the character.
 * @property imageUrl The URL of the character's image.
 *
 * @author Nicholas Rutherford
 */
data class CharacterImageUrlConverter(
    val id: Int,
    val characterName: String,
    val imageUrl: String
) {

    /**
     * Converts this converter to a CharacterImageUrlEntity
     */
    fun toEntity(): CharacterImageUrlEntity = CharacterImageUrlEntity(
        id = id,
        characterName = characterName,
        imageUrl = imageUrl
    )

    companion object{

        /**
         * Creates a CharacterImageUrlConverter from a CharacterImageUrlEntity.
         */
        fun fromEntity(entity: CharacterImageUrlEntity): CharacterImageUrlConverter =
            CharacterImageUrlConverter(
                id = entity.id,
                characterName = entity.characterName,
                imageUrl = entity.imageUrl
            )

        /**
         * Creates a CharacterImageUrlConverter from a CharacterImagesUrlJsonResponse.
         */
        fun fromJsonResponse(json: CharacterImagesUrlJsonResponse): CharacterImageUrlConverter =
            CharacterImageUrlConverter(
                id = json.id,
                characterName = json.characterName,
                imageUrl = json.imageUrl
            )
    }
}
