package com.nicholas.rutherford.potter.head.database.repository

import com.nicholas.rutherford.potter.head.database.converter.CharacterConverter
import com.nicholas.rutherford.potter.head.database.dao.CharacterDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * Implementation of CharacterRepository.
 * Handles the conversion between entities and converters.
 *
 * @param dao The DAO for accessing characters.
 *
 * @author Nicholas Rutherford
 */
class CharacterRepositoryImpl(private val dao: CharacterDao) : CharacterRepository {

    /**
     * Gets all characters from the database as a Flow.
     * Converts entities to converters for use in the domain layer.
     *
     * @return A [Flow] emitting a list of [CharacterConverter] objects.
     */
    override fun getAllCharacters(): Flow<List<CharacterConverter>> {
        return dao.getAllCharacters().map { entities ->
            entities.map { characterEntity -> CharacterConverter.fromEntity(entity = characterEntity) }
        }
    }

    /**
     * Gets a specific character by name from the database as a Flow.
     * Converts the entity to a converter for use in the domain layer.
     *
     * @param name The name of the character to fetch.
     * @return A [Flow] emitting a [CharacterConverter] object.
     */
    override fun getCharacterByName(name: String): Flow<CharacterConverter> {
        return dao.getCharacterByName(name).map { entity ->
            CharacterConverter.fromEntity(entity = entity)
        }
    }

    /**
     * Inserts a new character into the database.
     * Converts the converter to an entity before inserting.
     *
     * @param character The [CharacterConverter] to insert.
     */
    override suspend fun insertCharacter(character: CharacterConverter) = dao.insertCharacter(character = character.toEntity())

    /**
     * Updates an existing character in the database.
     * Converts the converter to an entity before updating.
     *
     * @param character The [CharacterConverter] to update.
     */
    override suspend fun updateCharacter(character: CharacterConverter) = dao.updateCharacter(character = character.toEntity())

    /**
     * Deletes a character from the database by name.
     *
     * @param name The name of the character to delete.
     */
    override suspend fun deleteCharacterByName(name: String) = dao.deleteCharacterByName(name)

    /**
     * Deletes all characters from the database.
     */
    override suspend fun deleteAllCharacters() = dao.deleteAllCharacters()
}