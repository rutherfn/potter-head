package com.nicholas.rutherford.potter.head.database.repository

import com.nicholas.rutherford.potter.head.database.converter.DebugToggleConverter
import kotlinx.coroutines.flow.Flow

/**
 * Repository interface for managing debug toggles.
 * Provides a clean way for accessing and modifying debug toggle state.
 *
 * @author Nicholas Rutherford
 */
interface DebugToggleRepository {

    /**
     * Gets all debug toggles as a Flow.
     */
    fun getAllToggles(): Flow<List<DebugToggleConverter>>

    /**
     * Gets a specific toggle by key as a Flow.
     */
    fun getToggle(key: String): Flow<DebugToggleConverter?>

    /**
     * Gets a specific toggle by key synchronously.
     */
    suspend fun getToggleSync(key: String): DebugToggleConverter?

    /**
     * Sets the enabled state of a toggle.
     */
    suspend fun setToggleEnabled(key: String, isEnabled: Boolean)

    /**
     * Checks if a toggle is enabled. Returns false if the toggle doesn't exist.
     */
    suspend fun isToggleEnabled(key: String): Boolean
}

