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
        val charactersWithMergedUrls = characters.map { character -> character.mergeImageUrlIfNeeded(imageUrlMap = imageUrlMap) }
        
        dao.insertAllCharacters(characters = charactersWithMergedUrls.map { it.toEntity() })
    }

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