package com.nicholas.rutherford.potter.head.feature.quizzes

/**
 * Parameters for the QuizzesScreen composable.
 *
 * @param state The current state of the quizzes screen.
 * @param onQuizClicked Callback for when a quiz is clicked. Receives title, description, and imageUrl.
 * @param onSavedQuizClicked Callback for when a saved quiz is clicked. Receives quizId.
 * @param onFilterItemClicked Callback for when a filter chip is clicked.
 * @param onRetryClicked Callback for when retry button is clicked.
 *
 * @author Nicholas Rutherford
 */
data class QuizzesParams(
    val state: QuizzesState,
    val onQuizClicked: (title: String, description: String, imageUrl: String) -> Unit,
    val onSavedQuizClicked: (quizId: Long) -> Unit,
    val onFilterItemClicked: (index: Int) -> Unit = {},
    val onRetryClicked: () -> Unit = {}
)
