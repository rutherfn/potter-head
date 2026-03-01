package com.nicholas.rutherford.potter.head.database.repository

import com.nicholas.rutherford.potter.head.database.CharacterFilterType
import com.nicholas.rutherford.potter.head.database.converter.CharacterFilterConverter
import kotlinx.coroutines.flow.Flow

/**
 * Repository interface for managing character filters.
 * Provides a clean way to access and modify cached character filters data in the database.
 *
 * @author Nicholas Rutherford
 */
interface CharacterFilterRepository {
    fun getCharacterFilters(): Flow<List<CharacterFilterConverter>>
    fun getCharacterFiltersByType(filterType: CharacterFilterType): Flow<List<CharacterFilterConverter>>
    fun getAllCharacterFiltersIsActive(isActive: Boolean): Flow<List<CharacterFilterConverter>>
    suspend fun insertFilter(filter: CharacterFilterConverter)
    suspend fun insertAllFilters(filters: List<CharacterFilterConverter>)
    suspend fun updateFilter(characterFilterConverter: CharacterFilterConverter)
    suspend fun deleteFilterByType(filterType: CharacterFilterType)
    suspend fun deleteAllFilters()
}