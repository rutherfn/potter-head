package com.nicholas.rutherford.potter.head.database.repository

import com.nicholas.rutherford.potter.head.database.converter.CharacterFilterConverter
import kotlinx.coroutines.flow.Flow

/**
 * Data class representing the primary filter flows used in character filtering.
 *
 * Primary filters are those that are combined in the initial combine operation
 * when retrieving all characters. These filters include:
 * - House filter
 * - Gender filter
 * - Species filter
 * - Hogwarts affiliation filter
 *
 * These filters are applied first in the filtering chain and are combined
 * together in a single combine operation for efficiency.
 *
 * @property houseFilterFlow Flow of house filter converters.
 * @property genderFilterFlow Flow of gender filter converters.
 * @property speciesFilterFlow Flow of species filter converters.
 * @property hogwartsAffiliationFilterFlow Flow of Hogwarts affiliation filter converters.
 *
 * @author Nicholas Rutherford
 */
data class PrimaryFilterFlows(
    val houseFilterFlow: Flow<List<CharacterFilterConverter>>,
    val genderFilterFlow: Flow<List<CharacterFilterConverter>>,
    val speciesFilterFlow: Flow<List<CharacterFilterConverter>>,
    val hogwartsAffiliationFilterFlow: Flow<List<CharacterFilterConverter>>
)
