package com.nicholas.rutherford.potter.head.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.nicholas.rutherford.potter.head.database.entity.CharacterEntity
import com.nicholas.rutherford.potter.head.database.entity.SpellEntity
import kotlinx.coroutines.flow.Flow

/**
 * Data Access Object for SpellEntity.
 * Provides methods to interact with the spell table in the Room database.
 *
 * @author Nicholas Rutherford
 */
@Dao
interface SpellDao {
    @Query("SELECT COUNT(*) FROM spells")
    suspend fun getSpellCount(): Int

    @Query("SELECT * FROM spells")
    fun getAllSpells(): Flow<List<SpellEntity>>

    @Query("SELECT * FROM spells WHERE name = :name")
    fun getSpellByName(name: String): Flow<SpellEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllSpells(spells: List<SpellEntity>)

    @Query("SELECT * FROM spells WHERE LOWER(TRIM(name)) LIKE '%' || LOWER(TRIM(:query)) || '%'")
    suspend fun searchSpell(query: String): List<SpellEntity>

    @Update
    suspend fun updateSpell(spellEntity: SpellEntity)

    @Query("DELETE FROM spells WHERE name = :name")
    suspend fun deleteSpellByName(name: String)

    @Query("DELETE FROM spells")
    suspend fun deleteAllSpells()
}