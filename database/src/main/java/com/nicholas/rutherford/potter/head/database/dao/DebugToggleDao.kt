package com.nicholas.rutherford.potter.head.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.nicholas.rutherford.potter.head.database.entity.DebugToggleEntity
import kotlinx.coroutines.flow.Flow

/**
 * Data Access Object for DebugToggleEntity.
 * Provides methods to interact with the debugToggles table.
 *
 * @author Nicholas Rutherford
 */
@Dao
interface DebugToggleDao {
    @Query("SELECT * FROM debugToggles")
    fun getAllToggles(): Flow<List<DebugToggleEntity>>

    @Query("SELECT * FROM debugToggles WHERE toggleKey = :key")
    fun getToggle(key: String): Flow<DebugToggleEntity?>

    @Query("SELECT * FROM debugToggles WHERE toggleKey = :key")
    suspend fun getToggleSync(key: String): DebugToggleEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertToggle(toggle: DebugToggleEntity)

    @Update
    suspend fun updateToggle(toggle: DebugToggleEntity)

    @Query("UPDATE debugToggles SET isEnabled = :isEnabled WHERE toggleKey = :key")
    suspend fun updateToggleState(key: String, isEnabled: Boolean)

    @Query("DELETE FROM debugToggles WHERE toggleKey = :key")
    suspend fun deleteToggle(key: String)

    @Query("DELETE FROM debugToggles")
    suspend fun deleteAllToggles()
}

