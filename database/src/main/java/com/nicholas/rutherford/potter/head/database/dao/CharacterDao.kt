package com.nicholas.rutherford.potter.head.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.nicholas.rutherford.potter.head.database.entity.CharacterEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CharacterDao {

    @Query("SELECT * FROM characters")
    fun getAllCharacters(): Flow<List<CharacterEntity>>

    @Query("SELECT * FROM characters WHERE name = :name")
    fun getCharacterByName(name: String): Flow<CharacterEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCharacter(character: CharacterEntity)

    @Update
    suspend fun updateCharacter(character: CharacterEntity)

    @Query("DELETE FROM characters WHERE name = :name")
    suspend fun deleteCharacterByName(name: String)

    @Query("DELETE FROM characters")
    suspend fun deleteAllCharacters()
}