package com.nicholas.rutherford.potter.head.feature.characters.characterfilters

/**
 * Defines UI data for the Character Filters Screen.
 * Defined and managed in the ViewModel.
 *
 * @param houseFiltersSelected The list of selected house filters.
 *
 * @author Nicholas Rutherford
 */
data class CharacterFiltersState(
    val houseFiltersSelected: List<String> = emptyList()
)