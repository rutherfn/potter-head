package com.nicholas.rutherford.potter.head.database.dao

import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.nicholas.rutherford.potter.head.database.CharacterFilterType
import com.nicholas.rutherford.potter.head.database.entity.CharacterFilterEntity
import kotlinx.coroutines.flow.Flow

/**
 * Data Access Object for CharacterFilterEntity.
 * Provides methods to interact with the character filters table in the Room database.
 *
 * @author Nicholas Rutherford
 */
interface CharacterFilterDao {

    @Query("SELECT * FROM characterFilters")
    fun getAllCharacterFilters(): Flow<List<CharacterFilterEntity>>

    @Query("SELECT * FROM characterFilters WHERE filterType = :filterType")
    fun getCharacterFiltersByType(filterType: CharacterFilterType): Flow<List<CharacterFilterEntity>>

    @Query("SELECT * FROM characterFilters WHERE isActive = :isActive")
    fun getAllCharacterFiltersIsActive(isActive: Boolean): Flow<List<CharacterFilterEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFilter(filter: CharacterFilterEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllFilters(filters: List<CharacterFilterEntity>)

    @Update
    suspend fun updateFilter(characterFilterEntity: CharacterFilterEntity)

    @Query("DELETE FROM characterFilters WHERE filterType = :filterType")
    suspend fun deleteFilterByType(filterType: CharacterFilterType)

    @Query("DELETE FROM characterFilters")
    suspend fun deleteAllFilters()
}