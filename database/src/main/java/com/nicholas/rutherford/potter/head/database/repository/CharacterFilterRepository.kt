package com.nicholas.rutherford.potter.head.database.repository

import com.nicholas.rutherford.potter.head.database.CharacterFilterType
import com.nicholas.rutherford.potter.head.database.DefaultFilters
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
    suspend fun getAllCharacterFiltersSync(): List<CharacterFilterConverter>
    fun getCharacterFiltersByType(filterType: CharacterFilterType): Flow<List<CharacterFilterConverter>>
    suspend fun getCharacterFiltersByTypeSync(filterType: CharacterFilterType): List<CharacterFilterConverter>
    fun getAllCharacterFiltersIsActive(isActive: Boolean): Flow<List<CharacterFilterConverter>>
    suspend fun insertFilter(filter: CharacterFilterConverter)
    suspend fun insertAllFilters(filters: List<CharacterFilterConverter>)
    suspend fun updateFilter(characterFilterConverter: CharacterFilterConverter)
    suspend fun deleteFilterByType(filterType: CharacterFilterType)
    suspend fun deleteAllFilters()
    suspend fun resetFilters()
}

/**
 * Extension function to get the count of active filters that are not at their default values.
 * A filter is considered at default if its values match exactly the default values for that filter type.
 *
 * @return The count of filters that differ from their default values.
 *
 * @author Nicholas Rutherford
 */
suspend fun CharacterFilterRepository.getActiveFilterCount(): Int {
    val allFilters = getAllCharacterFiltersSync()
    
    return allFilters.count { filter ->
        if (!filter.isActive) {
            false
        } else {
            !isFilterAtDefault(filter)
        }
    }
}

/**
 * Checks if a filter is at its default values.
 * For HOUSE filter, checks if it contains all default house values.
 * For other filter types, returns false (not at default) until defaults are defined.
 *
 * @param filter The filter to check.
 * @return true if the filter matches its default values, false otherwise.
 */
private fun isFilterAtDefault(filter: CharacterFilterConverter): Boolean {
    return when (filter.filterType) {
        CharacterFilterType.HOUSE -> isFilterValuesAtDefault(
            defaultFilter = DefaultFilters.HouseFilter,
            filter = filter
        )
        CharacterFilterType.GENDER -> isFilterValuesAtDefault(
            defaultFilter = DefaultFilters.genderFilter,
            filter = filter
        )
        CharacterFilterType.SPECIES -> isFilterValuesAtDefault(
            defaultFilter = DefaultFilters.speciesFilter,
            filter = filter
        )
        CharacterFilterType.HOGWARTS_AFFILIATION -> isFilterValuesAtDefault(
            defaultFilter = DefaultFilters.hogwartsAffiliationFilter,
            filter = filter
        )
        CharacterFilterType.WIZARD_STATUS -> isFilterValuesAtDefault(
            defaultFilter = DefaultFilters.isWizardFilter,
            filter = filter
        )
        CharacterFilterType.ALIVE_STATUS -> isFilterValuesAtDefault(
            defaultFilter = DefaultFilters.isAliveFilter,
            filter = filter
        )
    }
}

/**
 * Helper function to check if a filter's values match the default filter's values.
 *
 * @param defaultFilter The default filter to compare against.
 * @param filter The filter to check.
 * @return true if the filter values match the default filter values exactly, false otherwise.
 */
private fun isFilterValuesAtDefault(
    defaultFilter: CharacterFilterConverter,
    filter: CharacterFilterConverter
): Boolean {
    val defaultValues = defaultFilter.values.toSet()
    val filterValues = filter.values.toSet()
    return filterValues.containsAll(defaultValues) && defaultValues.containsAll(filterValues)
}