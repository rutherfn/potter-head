package com.nicholas.rutherford.potter.head.feature.characters.characters

/**
 * Parameters data class for the Characters Screen.
 *
 * @author Nicholas Rutherford
 */
data class CharactersParams(
    val state: CharactersState,
    val onCharacterClicked: (characterName: String) -> Unit,
    val onRetryClicked: () -> Unit,
    val onLoadMore: () -> Unit
)