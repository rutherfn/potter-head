package com.nicholas.rutherford.potter.head.database.repository

import com.nicholas.rutherford.potter.head.database.converter.DebugToggleConverter
import com.nicholas.rutherford.potter.head.database.dao.DebugToggleDao
import com.nicholas.rutherford.potter.head.database.entity.DebugToggleEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * Implementation of DebugToggleRepository.
 * Handles the conversion between entities and converters.
 *
 * @param dao The DAO for accessing debug toggles.
 *
 * @author Nicholas Rutherford
 */
class DebugToggleRepositoryImpl(
    private val dao: DebugToggleDao
) : DebugToggleRepository {

    override fun getAllToggles(): Flow<List<DebugToggleConverter>> {
        return dao.getAllToggles().map { entities ->
            entities.map { DebugToggleConverter.fromEntity(it) }
        }
    }

    override fun getToggle(key: String): Flow<DebugToggleConverter?> {
        return dao.getToggle(key).map { entity ->
            entity?.let { DebugToggleConverter.fromEntity(it) }
        }
    }

    override suspend fun getToggleSync(key: String): DebugToggleConverter? {
        return dao.getToggleSync(key)?.let { DebugToggleConverter.fromEntity(it) }
    }

    override suspend fun setToggleEnabled(key: String, isEnabled: Boolean) {
        val existingToggle = dao.getToggleSync(key)
        if (existingToggle != null) {
            // Update existing toggle
            dao.updateToggleState(key, isEnabled)
        } else {
            // Insert new toggle
            dao.insertToggle(
                DebugToggleEntity(
                    toggleKey = key,
                    isEnabled = isEnabled
                )
            )
        }
    }

    override suspend fun isToggleEnabled(key: String): Boolean {
        return dao.getToggleSync(key)?.isEnabled ?: false
    }
}

