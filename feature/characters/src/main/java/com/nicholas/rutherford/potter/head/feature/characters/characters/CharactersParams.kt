package com.nicholas.rutherford.potter.head.feature.characters.characters

import com.nicholas.rutherford.potter.head.database.converter.CharacterConverter

/**
 * Parameters data class for the Characters Screen.
 *
 * @param state The current state of the Characters Screen.
 * @param onCharacterClicked Callback for when a character is clicked.
 * @param onRetryClicked Callback for when the retry button is clicked.
 * @param onLoadMore Callback for when the user scrolls to the end of the list.
 * @param buildCharacterStatusIds Function to build the list of status IDs for a character.
 * @param onSearchQueryChange Callback for when the search query changes.
 * @param onClearClicked Callback for when the clear button is clicked.
 * @param onFilterClicked Callback for when the filter button is clicked.
 * @param onClearFiltersClicked Callback for when the clear filters button is clicked.
 *
 * @author Nicholas Rutherford
 */
data class CharactersParams(
    val state: CharactersState,
    val onCharacterClicked: (characterName: String) -> Unit,
    val onRetryClicked: () -> Unit,
    val onLoadMore: () -> Unit,
    val buildCharacterStatusIds: (CharacterConverter) -> List<Int>,
    val onSearchQueryChange: (String) -> Unit,
    val onClearClicked: () -> Unit,
    val onFilterClicked: () -> Unit,
    val onClearFiltersClicked: () -> Unit
)