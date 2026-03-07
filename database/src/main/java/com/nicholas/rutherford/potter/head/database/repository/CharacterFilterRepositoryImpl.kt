package com.nicholas.rutherford.potter.head.database.repository

import com.nicholas.rutherford.potter.head.database.CharacterFilterType
import com.nicholas.rutherford.potter.head.database.DefaultFilters
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

    override suspend fun getAllCharacterFiltersSync(): List<CharacterFilterConverter> {
        return dao.getAllCharacterFiltersSync().map { entity ->
            CharacterFilterConverter.fromEntity(entity = entity)
        }
    }

    override fun getCharacterFiltersByType(filterType: CharacterFilterType): Flow<List<CharacterFilterConverter>> {
        return dao.getCharacterFiltersByType(filterType = filterType).map { entities ->
            entities.map { characterFilterEntity -> CharacterFilterConverter.fromEntity(entity = characterFilterEntity) }
        }
    }

    override suspend fun getCharacterFiltersByTypeSync(filterType: CharacterFilterType): List<CharacterFilterConverter> {
        return dao.getCharacterFiltersByTypeSync(filterType = filterType).map { entity ->
            CharacterFilterConverter.fromEntity(entity = entity)
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

    override suspend fun resetFilters() {
        val defaultFiltersByType =
            mapOf(
                CharacterFilterType.HOUSE to DefaultFilters.HouseFilter,
                CharacterFilterType.GENDER to DefaultFilters.genderFilter,
                CharacterFilterType.SPECIES to DefaultFilters.speciesFilter,
                CharacterFilterType.HOGWARTS_AFFILIATION to DefaultFilters.hogwartsAffiliation
            )

        defaultFiltersByType.forEach { (filterType, defaultFilter) ->
            deleteFilterByType(filterType = filterType)
            insertFilter(filter = defaultFilter)
        }
    }
}