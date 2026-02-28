package com.nicholas.rutherford.potter.head.database.repository

import com.nicholas.rutherford.potter.head.database.converter.CharacterImageUrlConverter
import kotlinx.coroutines.flow.Flow
import com.nicholas.rutherford.potter.head.core.CharacterImageUrlReader

/**
 * Repository interface for managing character urls.
 * Provides a clean way to access and modify cached character urls data in the database.
 *
 * @author Nicholas Rutherford
 */
interface CharacterImageRepository {

    /**
     * Gets all characters images as a Flow list.
     */
    fun getAllCharacterImages(): Flow<List<CharacterImageUrlConverter>>

    /**
     * Gets a character image by id as a Flow
     */
    fun getCharacterImageUrlById(id: Int): Flow<CharacterImageUrlConverter?>

    /**
     * Gets a character image by name as a Flow
     */
    fun getCharacterImageUrlByName(name: String): Flow<CharacterImageUrlConverter>

    /**
     * Inserts multiple characters urls into the database in a single transaction.
     * This will use [CharacterImageUrlReader] to fetch all of the data from the json and insert it
     * into the table.
     */
    suspend fun insertAllCharacterImageUrls()

    /**
     * Deletes a character image url in the database by id.
     */
    suspend fun deleteCharacterImageUrlById(id: Int)

    /**
     * Clears all characters image urls in the database
     */
    suspend fun deleteAllCharacterImageUrls()
}