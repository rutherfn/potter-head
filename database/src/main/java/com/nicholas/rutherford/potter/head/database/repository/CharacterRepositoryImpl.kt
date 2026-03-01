package com.nicholas.rutherford.potter.head.database.repository

import com.nicholas.rutherford.potter.head.core.Constants
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

    private val houseUrlMap = mapOf(
        Constants.GRYFFINDOR_HOUSE.lowercase() to Constants.GRYFFINDOR_HOUSE_URL,
        Constants.RAVENCLAW_HOUSE.lowercase() to Constants.RAVENCLAW_HOUSE_URL,
        Constants.HUFFLEPUFF_HOUSE.lowercase() to Constants.HUFFLEPUFF_HOUSE_URL,
        Constants.SLYTHERIN_HOUSE.lowercase() to Constants.SLYTHERIN_HOUSE_URL
    )

    private fun buildConverterWithHouseUrl(characterConverter: CharacterConverter): CharacterConverter {
        val houseUrl = characterConverter.house?.lowercase()?.let { house -> houseUrlMap[house] }
        return houseUrl?.let { characterConverter.copy(image = it) } ?: characterConverter
    }

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

    override suspend fun searchCharacters(query: String): List<CharacterConverter> {
        val searchedCharacters = dao.searchCharacter(query = query).map { entity -> CharacterConverter.fromEntity(entity = entity) }

        return searchedCharacters
    }

    private fun CharacterConverter.mergeImageUrlIfNeeded(imageUrlMap: Map<String, CharacterImageUrlEntity>): CharacterConverter {
        return if (image.isNullOrBlank()) {
            imageUrlMap[name.trim().lowercase()]?.let { imageUrl ->
                copy(image = imageUrl.imageUrl)
            } ?: buildConverterWithHouseUrl(characterConverter = this)
        } else {
            this
        }
    }

    override suspend fun updateCharacter(character: CharacterConverter) = dao.updateCharacter(character = character.toEntity())

    override suspend fun deleteCharacterByName(name: String) = dao.deleteCharacterByName(name = name)

    override suspend fun deleteAllCharacters() = dao.deleteAllCharacters()
}