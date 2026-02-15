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

    /**
     * Gets all debug toggles from the database as a Flow.
     * Converts entities to converters for use in the domain layer.
     *
     * @return A [Flow] emitting a list of [DebugToggleConverter] objects.
     */
    override fun getAllToggles(): Flow<List<DebugToggleConverter>> {
        return dao.getAllToggles().map { entities ->
            entities.map { debugToggleEntity -> DebugToggleConverter.fromEntity(entity = debugToggleEntity) }
        }
    }

    /**
     * Gets a specific debug toggle by key from the database as a Flow.
     * Converts the entity to a converter for use in the domain layer.
     *
     * @param key The key of the toggle to fetch.
     * @return A [Flow] emitting a [DebugToggleConverter] object, or null if not found.
     */
    override fun getToggle(key: String): Flow<DebugToggleConverter?> {
        return dao.getToggle(key).map { entity ->
            entity?.let { debugToggleEntity -> DebugToggleConverter.fromEntity(entity = debugToggleEntity) }
        }
    }

    /**
     * Gets a specific debug toggle by key synchronously.
     * Converts the entity to a converter for use in the domain layer.
     *
     * @param key The key of the toggle to fetch.
     * @return A [DebugToggleConverter] object, or null if not found.
     */
    override suspend fun getToggleSync(key: String): DebugToggleConverter? = dao.getToggleSync(key)?.let { debugToggleEntity -> DebugToggleConverter.fromEntity(entity = debugToggleEntity) }

    /**
     * Sets the enabled state of a debug toggle.
     * If the toggle exists, it will be updated. If it doesn't exist, it will be created.
     *
     * @param key The key of the toggle to set.
     * @param isEnabled Whether the toggle should be enabled.
     */
    override suspend fun setToggleEnabled(key: String, isEnabled: Boolean) {
        val existingToggle = dao.getToggleSync(key)
        if (existingToggle != null) {
            dao.updateToggleState(key, isEnabled)
        } else {
            dao.insertToggle(
                DebugToggleEntity(
                    toggleKey = key,
                    isEnabled = isEnabled
                )
            )
        }
    }

    /**
     * Checks if a toggle is enabled.
     * Returns false if the toggle doesn't exist.
     *
     * @param key The key of the toggle to check.
     * @return true if the toggle is enabled, false otherwise or if the toggle doesn't exist.
     */
    override suspend fun isToggleEnabled(key: String): Boolean = dao.getToggleSync(key)?.isEnabled ?: false
}

