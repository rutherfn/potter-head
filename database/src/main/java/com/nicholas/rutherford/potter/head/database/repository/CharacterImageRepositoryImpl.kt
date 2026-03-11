package com.nicholas.rutherford.potter.head.database.repository

import android.content.Context
import com.nicholas.rutherford.potter.head.core.JsonReader
import com.nicholas.rutherford.potter.head.database.converter.CharacterImageUrlConverter
import com.nicholas.rutherford.potter.head.database.dao.CharacterImageDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * Implementation of CharacterImageRepository.
 * Handles the conversion between entities and converters.
 *
 * @param dao The DAO for accessing character urls.
 * @param context The application context uses to call [JsonReader]
 *
 * @author Nicholas Rutherford
 */
class CharacterImageRepositoryImpl(
    private val dao: CharacterImageDao,
    private val context: Context
): CharacterImageRepository {
    override fun getAllCharacterImages(): Flow<List<CharacterImageUrlConverter>> {
        return dao.getAllCharacterImageUrls().map { entities ->
            entities.map { characterImageUrlEntity -> CharacterImageUrlConverter.fromEntity(entity = characterImageUrlEntity) }
        }
    }

    override fun getCharacterImageUrlById(id: Int): Flow<CharacterImageUrlConverter?> {
        return dao.getCharacterImageUrlById(id = id).map { entity ->
            entity?.let { characterImageUrlEntity -> CharacterImageUrlConverter.fromEntity(entity = characterImageUrlEntity) }
        }
    }

    override fun getCharacterImageUrlByName(name: String): Flow<CharacterImageUrlConverter?> {
        return dao.getCharacterImageUrlByName(characterName = name).map { entity ->
            entity?.let { characterImageUrlEntity -> CharacterImageUrlConverter.fromEntity(entity = characterImageUrlEntity) }
        }
    }

    override suspend fun insertAllCharacterImageUrls() {
        val characterUrlsJson = JsonReader.getCharacterImageUrls(context = context)
        val test = JsonReader.getQuizzes(context = context)

        println("here is the full data $test")
        val characterImageUrlConverters = characterUrlsJson.map { jsonResponse -> CharacterImageUrlConverter.fromJsonResponse(json = jsonResponse) }
        
        dao.insertAllCharacterImageUrls(characterImageUrls = characterImageUrlConverters.map { converter -> converter.toEntity() })
    }

    override suspend fun deleteCharacterImageUrlById(id: Int) = dao.deleteCharacterImageUrlById(id = id)

    override suspend fun deleteAllCharacterImageUrls() = dao.deleteAllCharacterImageUrls()
}