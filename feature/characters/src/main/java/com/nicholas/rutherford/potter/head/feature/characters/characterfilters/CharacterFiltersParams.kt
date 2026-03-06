package com.nicholas.rutherford.potter.head.feature.characters.characterfilters

import com.nicholas.rutherford.potter.head.database.CharacterFilterType

/**
 * Parameters data class for the Character Filters Screen.
 *
 * @param state The current state of the Character Filters Screen.
 * @param houses The list of available houses.
 * @param genders The list of available genders.
 * @param onFilterHouseClicked Callback for when a house filter is clicked.
 * @param onFilterGenderClicked Callback for when a gender filter is clicked.
 *
 * @author Nicholas Rutherford
 */
data class CharacterFiltersParams(
    val state: CharacterFiltersState,
    val houses: List<String>,
    val genders: List<String>,
    val onFilterHouseClicked: (type: CharacterFilterType, value: String) -> Unit,
    val onFilterGenderClicked: (type: CharacterFilterType, value: String) -> Unit
)