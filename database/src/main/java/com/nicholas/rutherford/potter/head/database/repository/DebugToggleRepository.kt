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
    fun getAllToggles(): Flow<List<DebugToggleConverter>>

    fun getToggle(key: String): Flow<DebugToggleConverter?>

    suspend fun getToggleSync(key: String): DebugToggleConverter?

    suspend fun setToggleEnabled(key: String, isEnabled: Boolean)

    suspend fun isToggleEnabled(key: String): Boolean
}



