package com.nicholas.rutherford.potter.head.database.repository

import com.nicholas.rutherford.potter.head.database.converter.CharacterConverter
import com.nicholas.rutherford.potter.head.database.dao.CharacterDao
import com.nicholas.rutherford.potter.head.database.dao.CharacterImageDao
import com.nicholas.rutherford.potter.head.database.entity.CharacterImageUrlEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * Implementation of CharacterRepository.
 * Handles the conversion between entities and converters.
 *
 * @param dao The DAO for accessing characters.
 * @param characterImageDao The DAO for accessing character urls.
 *
 * @author Nicholas Rutherford
 */
class CharacterRepositoryImpl(
    private val dao: CharacterDao,
    private val characterImageDao: CharacterImageDao
) : CharacterRepository {

    override fun getAllCharacters(): Flow<List<CharacterConverter>> {
        return dao.getAllCharacters().map { entities ->
            entities.map { characterEntity -> CharacterConverter.fromEntity(entity = characterEntity) }
        }
    }

    override fun getCharacterByName(name: String): Flow<CharacterConverter> {
        return dao.getCharacterByName(name).map { entity ->
            CharacterConverter.fromEntity(entity = entity)
        }
    }

    override suspend fun insertCharacter(character: CharacterConverter) = dao.insertCharacter(character = character.toEntity())

    override suspend fun insertAllCharacters(characters: List<CharacterConverter>) {
        val imageUrlMap = characterImageDao.getAllCharacterImageUrlsSync().associateBy { entity -> entity.characterName.trim().lowercase() }
        val charactersWithMergedUrls = characters.map { character -> character.mergeImageUrlIfNeeded(imageUrlMap) }
        
        dao.insertAllCharacters(characters = charactersWithMergedUrls.map { it.toEntity() })
    }

    /**
     * Merges the image URL from the map if the character needs one.
     * If the character already has an image URL, it returns the character unchanged.
     * If the character needs an image URL, it looks it up in the map and returns a copy with the URL if found.
     *
     * @param imageUrlMap Map of normalized character names to their image URL entities.
     * @return The character with merged image URL if needed, otherwise the original character.
     */
    private fun CharacterConverter.mergeImageUrlIfNeeded(imageUrlMap: Map<String, CharacterImageUrlEntity>): CharacterConverter {
        
        if (image.isNullOrBlank()) {
            return imageUrlMap[name.trim().lowercase()]?.let { imageUrl ->
                copy(image = imageUrl.imageUrl)
            } ?: this
        } else {
            return this
        }
    }

    override suspend fun updateCharacter(character: CharacterConverter) = dao.updateCharacter(character = character.toEntity())

    override suspend fun deleteCharacterByName(name: String) = dao.deleteCharacterByName(name = name)

    override suspend fun deleteAllCharacters() = dao.deleteAllCharacters()
}