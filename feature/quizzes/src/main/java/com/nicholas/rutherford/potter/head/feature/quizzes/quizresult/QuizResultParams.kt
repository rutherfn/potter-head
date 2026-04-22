package com.nicholas.rutherford.potter.head.feature.quizzes.quizresult

/**
 * Parameters for the QuizResultScreen composable.
 *
 * @param state The current state of the quiz result screen.
 * @param onViewResultsClicked Callback for when the view results button is clicked.
 * @param onHideResultsClicked Callback for when the hide results button is clicked.
 * @param onContinueClicked Callback for when the continue button is clicked
 *
 * @author Nicholas Rutherford
 */
data class QuizResultParams(
    val state: QuizResultState,
    val onViewResultsClicked: () -> Unit,
    val onHideResultsClicked: () -> Unit,
    val onContinueClicked: () -> Unit
)
