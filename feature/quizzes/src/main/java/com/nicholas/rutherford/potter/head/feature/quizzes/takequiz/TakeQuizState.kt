package com.nicholas.rutherford.potter.head.feature.quizzes.takequiz

import com.nicholas.rutherford.potter.head.database.entity.QuestionEntity

/**
 * Defines UI data for the Take Quiz State Screen.
 * Defined and managed in the ViewModel.
 *
 * @param questions The list of questions to be displayed.
 * @param questionSize The total number of questions.
 * @param currentQuestionNumber The 1-based current question number.
 * @param selectedAnswerIndex The 0-based index of the currently selected answer, or null if none.
 *
 * @author Nicholas Rutherford
 */
data class TakeQuizState(
    val questions: List<QuestionEntity> = emptyList(),
    val questionSize: Int = 0,
    val currentQuestionNumber: Int = 1,
    val selectedAnswerIndex: Int? = null
)
