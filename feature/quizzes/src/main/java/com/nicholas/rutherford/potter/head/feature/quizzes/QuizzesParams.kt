package com.nicholas.rutherford.potter.head.feature.quizzes

/**
 * Parameters for the QuizzesScreen composable.
 *
 * @param state The current state of the quizzes screen.
 * @param onQuizClicked Callback for when a quiz is clicked.
 * @param onFilterItemClicked Callback for when a filter chip is clicked.
 * @param onRetryClicked Callback for when retry button is clicked.
 *
 * @author Nicholas Rutherford
 */
data class QuizzesParams(
    val state: QuizzesState,
    val onQuizClicked: () -> Unit,
    val onFilterItemClicked: (index: Int) -> Unit = {},
    val onRetryClicked: () -> Unit = {}
)