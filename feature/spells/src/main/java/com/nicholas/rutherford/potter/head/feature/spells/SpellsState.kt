package com.nicholas.rutherford.potter.head.feature.spells

import com.nicholas.rutherford.potter.head.database.converter.SpellConverter

/**
 * Defines UI data for the Spells Screen.
 * Defined and managed in the ViewModel.
 *
 * @param spells The list of spells to display.
 * @param errorType The type of error to display.
 * @param isLoading Whether the screen is currently loading data.
 * @param searchQuery The current search query.
 *
 * @author Nicholas Rutherford
 */
data class SpellsState(
    val spells: List<SpellConverter> = emptyList(),
    val errorType: SpellsErrorType = SpellsErrorType.NONE,
    val isLoading: Boolean = true,
    val searchQuery: String = ""
)