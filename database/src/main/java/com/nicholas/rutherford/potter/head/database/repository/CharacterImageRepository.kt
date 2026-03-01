package com.nicholas.rutherford.potter.head.database.repository

import com.nicholas.rutherford.potter.head.database.converter.CharacterImageUrlConverter
import kotlinx.coroutines.flow.Flow

/**
 * Repository interface for managing character urls.
 * Provides a clean way to access and modify cached character urls data in the database.
 *
 * @author Nicholas Rutherford
 */
interface CharacterImageRepository {
    fun getAllCharacterImages(): Flow<List<CharacterImageUrlConverter>>
    fun getCharacterImageUrlById(id: Int): Flow<CharacterImageUrlConverter?>
    fun getCharacterImageUrlByName(name: String): Flow<CharacterImageUrlConverter?>
    suspend fun insertAllCharacterImageUrls()
    suspend fun deleteCharacterImageUrlById(id: Int)
    suspend fun deleteAllCharacterImageUrls()
}