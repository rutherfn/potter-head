package com.nicholas.rutherford.potter.head.feature.quizzes.quizdetail

/**
 * Parameters for the QuizDetailScreen composable.
 *
 * @param onStartQuizClicked Callback for when the start quiz button is clicked.
 * @param state The current state of the quiz detail screen.
 *
 * @author Nicholas Rutherford
 */
data class QuizDetailParams(
    val onStartQuizClicked: () -> Unit,
    val state: QuizDetailState
)