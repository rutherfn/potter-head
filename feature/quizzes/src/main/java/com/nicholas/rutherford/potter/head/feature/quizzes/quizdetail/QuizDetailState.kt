package com.nicholas.rutherford.potter.head.feature.quizzes.quizdetail

/**
 * Defines UI data for the Quiz Detail State Screen.
 * Defined and managed in the ViewModel.
 *
 * @param title The title of the quiz.
 * @param description The description of the quiz.
 * @param imageUrl The URL of the quiz image.
 *
 * @author Nicholas Rutherford
 */
data class QuizDetailState(
    val title: String = "",
    val description: String = "",
    val imageUrl: String = ""
)