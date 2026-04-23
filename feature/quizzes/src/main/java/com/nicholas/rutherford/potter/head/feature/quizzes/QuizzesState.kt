package com.nicholas.rutherford.potter.head.feature.quizzes

import com.nicholas.rutherford.potter.head.core.DataErrorType
import com.nicholas.rutherford.potter.head.feature.quizzes.ext.QuizzesConverter

/**
 * Defines UI data for the Quizzes State Screen.
 *
 * @param quizzes The list of quizzes to display.
 * @param selectedFilterIndex The index of the currently selected filter.
 * @param filterTypes The list of filter types.
 * @param errorType The type of error to display.
 * @param isLoading Whether the screen is currently loading data.
 * @param shouldShowFilterChips Whether to show the filter chips on the Quiz screen.
 *
 * @author Nicholas Rutherford
 */
data class QuizzesState(
    val quizzes: List<QuizzesConverter> = emptyList(),
    val selectedFilterIndex: Int = 0,
    val filterTypes: List<String> = emptyList(),
    val errorType: DataErrorType = DataErrorType.None,
    val isLoading: Boolean = true,
    val shouldShowFilterChips: Boolean = false
)
