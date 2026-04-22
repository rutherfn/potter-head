package com.nicholas.rutherford.potter.head.feature.characters.characterfilters

/**
 * Defines UI data for the Character Filters Screen.
 * Defined and managed in the ViewModel.
 *
 * @param houseFiltersSelected The list of selected house filters.
 * @param genderFiltersSelected The list of selected gender filters.
 * @param speciesFiltersSelected The list of selected species filters.
 * @param hogwartsAffiliationsSelected The list of selected Hogwarts affiliation filters.
 * @param wizardStatusFiltersSelected The list of selected wizard status filters.
 * @param aliveStatusFiltersSelected The list of selected alive status filters.
 *
 * @author Nicholas Rutherford
 */
data class CharacterFiltersState(
    val houseFiltersSelected: List<String> = emptyList(),
    val genderFiltersSelected: List<String> = emptyList(),
    val speciesFiltersSelected: List<String> = emptyList(),
    val hogwartsAffiliationsSelected: List<String> = emptyList(),
    val wizardStatusFiltersSelected: List<String> = emptyList(),
    val aliveStatusFiltersSelected: List<String> = emptyList()
)
