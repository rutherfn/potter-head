package com.nicholas.rutherford.potter.head.database.repository

import com.nicholas.rutherford.potter.head.database.converter.CharacterFilterConverter
import kotlinx.coroutines.flow.Flow

/**
 * Data class representing the additional filter flows used in character filtering.
 *
 * Additional filters are those that are applied after the primary filters
 * using nested combine operations. These filters include:
 * - Wizard status filter
 * - Alive status filter
 *
 * These filters are applied in a separate combine operation because Kotlin Flow's
 * combine function supports a maximum of 5 flows. Since we have 6 total filters
 * (4 primary + 2 additional), we use nested combines to handle all filters.
 *
 * @property wizardFilterFlow Flow of wizard status filter converters.
 * @property aliveStatusFilterFlow Flow of alive status filter converters.
 *
 * @author Nicholas Rutherford
 */
data class AdditionalFilterFlows(
    val wizardFilterFlow: Flow<List<CharacterFilterConverter>>,
    val aliveStatusFilterFlow: Flow<List<CharacterFilterConverter>>
)

