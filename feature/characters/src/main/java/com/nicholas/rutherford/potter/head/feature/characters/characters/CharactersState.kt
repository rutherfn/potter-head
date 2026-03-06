package com.nicholas.rutherford.potter.head.feature.characters.characters

import com.nicholas.rutherford.potter.head.core.Constants
import com.nicholas.rutherford.potter.head.database.converter.CharacterConverter

/**
 * Defines UI data for the Characters Screen.
 * Defined and managed in the ViewModel.
 *
 * @param characters The list of characters to display.
 * @param errorType The type of error to display.
 * @param isLoading Whether the screen is currently loading data.
 * @param shouldShowNoContent Whether to show no content while loading data in database it will override the Shimmer effect.
 * @param isLoadingMore Whether the screen is currently loading more data.
 * @param hasMoreToLoad Whether there is more data to load.
 * @param pageSize The number of items to load at a time.
 * @param searchQuery The current search query text.
 * @param filterCount The number of active filters.
 *
 * @author Nicholas Rutherford
 */
data class CharactersState(
    val characters: List<CharacterConverter> = emptyList(),
    val errorType: CharactersErrorType = CharactersErrorType.NONE,
    val isLoading: Boolean = true,
    val shouldShowNoContent: Boolean = false,
    val isLoadingMore: Boolean = false,
    val hasMoreToLoad: Boolean = true,
    val pageSize: Int = Constants.INITIAL_PAGE_SIZE,
    val searchQuery: String = "",
    val filterCount: Int = 0
)