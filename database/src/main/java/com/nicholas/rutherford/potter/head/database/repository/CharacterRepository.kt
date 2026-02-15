package com.nicholas.rutherford.potter.head.database.repository

import com.nicholas.rutherford.potter.head.database.converter.CharacterConverter
import kotlinx.coroutines.flow.Flow

/**
 * Repository interface for managing characters.
 * Provides a clean way for accessing and modifying debug toggle state.
 *
 * @author Nicholas Rutherford
 */
interface CharacterRepository {

    /**
     * Gets all characters as a Flow List.
     */
    fun getAllCharacters(): Flow<List<CharacterConverter>>

    /**
     * Gets a character by name as a Flow.
     */
    fun getCharacterByName(name: String): Flow<CharacterConverter>

    /**
     * Creates a new character.
     */
    suspend fun insertCharacter(character: CharacterConverter)

    /**
     * Updates a current character in the database
     */
    suspend fun updateCharacter(character: CharacterConverter)

    /**
     * Deletes a character existing in the database.
     */
    suspend fun deleteCharacterByName(name: String)

    /**
     * Clears all characters in the database
     */
    suspend fun deleteAllCharacters()
}