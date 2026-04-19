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
class DebugToggleRepositoryImpl(private val dao: DebugToggleDao) : DebugToggleRepository {
    override fun getAllToggles(): Flow<List<DebugToggleConverter>> {
        return dao.getAllToggles().map { entities ->
            entities.map { debugToggleEntity -> DebugToggleConverter.fromEntity(entity = debugToggleEntity) }
        }
    }

    override fun getToggle(key: String): Flow<DebugToggleConverter?> {
        return dao.getToggle(key).map { entity ->
            entity?.let { debugToggleEntity -> DebugToggleConverter.fromEntity(entity = debugToggleEntity) }
        }
    }

    override suspend fun getToggleSync(key: String): DebugToggleConverter? = dao.getToggleSync(key)?.let { debugToggleEntity -> DebugToggleConverter.fromEntity(entity = debugToggleEntity) }

    override suspend fun setToggleEnabled(key: String, isEnabled: Boolean) {
        val existingToggle = dao.getToggleSync(key)
        if (existingToggle != null) {
            dao.updateToggleState(key, isEnabled)
        } else {
            dao.insertToggle(toggle = DebugToggleEntity(toggleKey = key, isEnabled = isEnabled))
        }
    }

    override suspend fun isToggleEnabled(key: String): Boolean = dao.getToggleSync(key)?.isEnabled ?: false
}
