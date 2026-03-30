package com.nicholas.rutherford.potter.head.feature.quizzes.takequiz

/**
 * Parameters for the TakeQuizScreen composable.
 *
 * @param state The current state of the take quiz screen.
 * @param onAnswerSelected Called when the user selects or deselects an answer (0-based index).
 * @param onContinueClicked Called when the user taps Continue. Receives current question number (1-based), total question size, and selected answer index (or null).
 *
 * @author Nicholas Rutherford
 */
data class TakeQuizParams(
    val state: TakeQuizState,
    val onAnswerSelected: (answerIndex: Int) -> Unit = {},
    val onContinueClicked: (currentQuestionNumber: Int, questionSize: Int, selectedAnswerIndex: Int?) -> Unit = { _, _, _ -> }
)