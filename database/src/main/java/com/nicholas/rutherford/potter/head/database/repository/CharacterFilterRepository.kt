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
 * Extension function to get the count of filter options that differ from their default values.
 * Each toggled option increments or decrements the count by one, regardless of category.
 * For example, deselecting Ravenclaw and Slytherin from defaults counts as 2, and changing gender to Female counts as 3.
 *
 * @return The total number of filter options that differ from default across all categories.
 *
 * @author Nicholas Rutherford
 */
fun List<CharacterFilterConverter>.getActiveFilterSelectionCount(): Int {
    var selectionCount = 0

    filter { filter -> filter.isActive }
        .groupBy { filter -> filter.filterType }
        .forEach { groupedFilters ->
            val filter = groupedFilters.value.firstOrNull() ?: return@forEach
            selectionCount += getSelectionDeltaFromDefault(filter = filter)
        }

    return selectionCount
}

/**
 * Extension function to get the count of active filters that are not at their default values.
 * A filter is considered at default if its values match exactly the default values for that filter type.
 *
 * @return The total number of filter options that differ from default across all categories.
 *
 * @author Nicholas Rutherford
 */
suspend fun CharacterFilterRepository.getActiveFilterCount(): Int {
    return getAllCharacterFiltersSync().getActiveFilterSelectionCount()
}

/**
 * Checks if a filter is at its default values.
 *
 * @param filter The filter to check.
 * @return true if the filter matches its default values, false otherwise.
 */
private fun isFilterAtDefault(filter: CharacterFilterConverter): Boolean {
    return isFilterValuesAtDefault(
        defaultFilter = getDefaultFilter(filterType = filter.filterType),
        filter = filter,
    )
}

private fun getDefaultFilter(filterType: CharacterFilterType): CharacterFilterConverter {
    return when (filterType) {
        CharacterFilterType.HOUSE -> DefaultFilters.HouseFilter
        CharacterFilterType.GENDER -> DefaultFilters.genderFilter
        CharacterFilterType.SPECIES -> DefaultFilters.speciesFilter
        CharacterFilterType.HOGWARTS_AFFILIATION -> DefaultFilters.hogwartsAffiliationFilter
        CharacterFilterType.WIZARD_STATUS -> DefaultFilters.isWizardFilter
        CharacterFilterType.ALIVE_STATUS -> DefaultFilters.isAliveFilter
    }
}

private fun getSelectionDeltaFromDefault(filter: CharacterFilterConverter): Int {
    val defaultValues = getDefaultFilter(filterType = filter.filterType).values.toSet()
    val currentValues = filter.values.toSet()

    return (defaultValues - currentValues).size + (currentValues - defaultValues).size
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
