package com.nicholas.rutherford.potter.head.database.repository

import com.nicholas.rutherford.potter.head.core.CharacterImageUrlReader
import com.nicholas.rutherford.potter.head.database.CharacterFilterType
import com.nicholas.rutherford.potter.head.database.converter.CharacterFilterConverter
import com.nicholas.rutherford.potter.head.database.dao.CharacterFilterDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * Implementation of CharacterFilterRepository.
 * Handles the conversion between entities and converters.
 *
 * @param dao The DAO for accessing character filters.
 *
 * @author Nicholas Rutherford
 */
class CharacterFilterRepositoryImpl(private val dao: CharacterFilterDao) : CharacterFilterRepository {

    override fun getCharacterFilters(): Flow<List<CharacterFilterConverter>> {
        return dao.getAllCharacterFilters().map { entities ->
            entities.map { characterFilterEntity -> CharacterFilterConverter.fromEntity(entity = characterFilterEntity) }
        }
    }

    override fun getCharacterFiltersByType(filterType: CharacterFilterType): Flow<List<CharacterFilterConverter>> {
        return dao.getCharacterFiltersByType(filterType = filterType).map { entities ->
            entities.map { characterFilterEntity -> CharacterFilterConverter.fromEntity(entity = characterFilterEntity) }
        }
    }

    override fun getAllCharacterFiltersIsActive(isActive: Boolean): Flow<List<CharacterFilterConverter>> {
        return dao.getAllCharacterFiltersIsActive(isActive = isActive).map { entities ->
            entities.map { characterFilterEntity -> CharacterFilterConverter.fromEntity(entity = characterFilterEntity) }
        }
    }

    override suspend fun insertFilter(filter: CharacterFilterConverter) = dao.insertFilter(filter = filter.toEntity())

    override suspend fun insertAllFilters(filters: List<CharacterFilterConverter>) {
        val filterEntities = filters.map { converter -> converter.toEntity() }

        dao.insertAllFilters(filters = filterEntities)
    }

    override suspend fun updateFilter(characterFilterConverter: CharacterFilterConverter) = dao.updateFilter(characterFilterEntity = characterFilterConverter.toEntity())

    override suspend fun deleteFilterByType(filterType: CharacterFilterType) = dao.deleteFilterByType(filterType = filterType)

    override suspend fun deleteAllFilters() = dao.deleteAllFilters()
}