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

fun List<CharacterFilterConverter>.getActiveFilterSelectionCount(): Int {
    var selectionCount = 0

    filter { filter -> filter.isActive }
        .groupBy { filter -> filter.filterType }
        .forEach { groupedFilters ->
            groupedFilters.value.firstOrNull()?.let { filter ->
                selectionCount += getSelectionDeltaFromDefault(filter = filter)
            }
        }

    return selectionCount
}

suspend fun CharacterFilterRepository.getActiveFilterCount(): Int {
    return getAllCharacterFiltersSync().getActiveFilterSelectionCount()
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
    val selectionDelta = (defaultValues - currentValues).size + (currentValues - defaultValues).size

    return selectionDelta
}
