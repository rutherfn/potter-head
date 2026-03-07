package com.nicholas.rutherford.potter.head.database.repository

import com.nicholas.rutherford.potter.head.database.converter.CharacterConverter
import kotlinx.coroutines.flow.Flow

/**
 * Repository interface for managing characters.
 * Provides a clean way to access and modify cached character data in the database.
 *
 * @author Nicholas Rutherford
 */
interface CharacterRepository {
    suspend fun getCharacterCount(): Int

    fun getAllCharacters(): Flow<List<CharacterConverter>>

    fun getCharacterByName(name: String): Flow<CharacterConverter>

    suspend fun insertCharacter(character: CharacterConverter)

    suspend fun insertAllCharacters(characters: List<CharacterConverter>)

    suspend fun searchCharacters(query: String): List<CharacterConverter>

    suspend fun updateCharacter(character: CharacterConverter)

    suspend fun deleteCharacterByName(name: String)

    suspend fun deleteAllCharacters()
}