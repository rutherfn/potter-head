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
 * Provides methods to interact with the debug_toggles table.
 *
 * @author Nicholas Rutherford
 */
@Dao
interface DebugToggleDao {
    /**
     * Gets all debug toggles as a Flow.
     */
    @Query("SELECT * FROM debug_toggles")
    fun getAllToggles(): Flow<List<DebugToggleEntity>>

    /**
     * Gets a specific toggle by key as a Flow.
     */
    @Query("SELECT * FROM debug_toggles WHERE toggleKey = :key")
    fun getToggle(key: String): Flow<DebugToggleEntity?>

    /**
     * Gets a specific toggle by key synchronously.
     */
    @Query("SELECT * FROM debug_toggles WHERE toggleKey = :key")
    suspend fun getToggleSync(key: String): DebugToggleEntity?

    /**
     * Inserts a debug toggle, replacing if it already exists.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertToggle(toggle: DebugToggleEntity)

    /**
     * Updates a debug toggle.
     */
    @Update
    suspend fun updateToggle(toggle: DebugToggleEntity)

    /**
     * Updates the enabled state of a toggle by key.
     */
    @Query("UPDATE debug_toggles SET isEnabled = :isEnabled WHERE toggleKey = :key")
    suspend fun updateToggleState(key: String, isEnabled: Boolean)

    /**
     * Deletes a toggle by key.
     */
    @Query("DELETE FROM debug_toggles WHERE toggleKey = :key")
    suspend fun deleteToggle(key: String)

    /**
     * Deletes all toggles.
     */
    @Query("DELETE FROM debug_toggles")
    suspend fun deleteAllToggles()
}

