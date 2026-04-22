package com.nicholas.rutherford.potter.head.feature.spells

/**
 * Parameters data class for the Spells Screen.
 *
 * @param state The current state of the Spells Screen.
 * @param onRetryClicked Callback for when the retry button is clicked.
 * @param onSearchQueryChange Callback for when the search query changes.
 * @param onClearClicked Callback for when the clear button is clicked.
 *
 * @author Nicholas Rutherford
 */
data class SpellsParams(
    val state: SpellsState,
    val onRetryClicked: () -> Unit,
    val onSearchQueryChange: (String) -> Unit,
    val onClearClicked: () -> Unit
)
