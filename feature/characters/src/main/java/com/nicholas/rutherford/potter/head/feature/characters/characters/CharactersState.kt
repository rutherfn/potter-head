package com.nicholas.rutherford.potter.head.feature.characters.characters

import com.nicholas.rutherford.potter.head.core.Constants
import com.nicholas.rutherford.potter.head.database.converter.CharacterConverter

data class CharactersState(
    val characters: List<CharacterConverter> = emptyList(),
    val errorType: CharactersErrorType = CharactersErrorType.NONE,
    val isLoading: Boolean = true,
    val isLoadingMore: Boolean = false,
    val hasMoreToLoad: Boolean = true,
    val pageSize: Int = Constants.INITIAL_PAGE_SIZE
)