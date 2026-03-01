package com.nicholas.rutherford.potter.head.feature.characters.characters

import com.nicholas.rutherford.potter.head.database.converter.CharacterConverter

/**
 * Parameters data class for the Characters Screen.
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
    val onFilterClick: () -> Unit
)