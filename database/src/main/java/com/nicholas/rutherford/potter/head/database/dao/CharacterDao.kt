package com.nicholas.rutherford.potter.head.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.nicholas.rutherford.potter.head.database.entity.CharacterEntity
import kotlinx.coroutines.flow.Flow

/**
 * Data Access Object for CharacterEntity.
 * Provides methods to interact with the characters table in the Room database.
 *
 * @author Nicholas Rutherford
 */
@Dao
interface CharacterDao {
    @Query("SELECT COUNT(*) FROM characters")
    suspend fun getCharacterCount(): Int

    @Query("SELECT * FROM characters")
    fun getAllCharacters(): Flow<List<CharacterEntity>>

    @Query("SELECT * FROM characters WHERE name = :name")
    fun getCharacterByName(name: String): Flow<CharacterEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCharacter(character: CharacterEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllCharacters(characters: List<CharacterEntity>)

    @Query("SELECT * FROM characters WHERE LOWER(TRIM(name)) LIKE '%' || LOWER(TRIM(:query)) || '%'")
    suspend fun searchCharacter(query: String): List<CharacterEntity>

    @Update
    suspend fun updateCharacter(character: CharacterEntity)

    @Query("DELETE FROM characters WHERE name = :name")
    suspend fun deleteCharacterByName(name: String)

    @Query("DELETE FROM characters")
    suspend fun deleteAllCharacters()
}