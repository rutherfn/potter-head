package com.nicholas.rutherford.potter.head.feature.quizzes.quizresult

import com.nicholas.rutherford.potter.head.database.entity.SavedQuestionItem

/**
 * Defines UI data for the Quiz Result State Screen.
 * Defined and managed in the ViewModel.
 *
 * @param quizTitle The title of the quiz.
 * @param quizImageUrl Cover image URL for the quiz (from saved attempt).
 * @param resultText The result of the quiz.
 * @param questions The list of questions with answers.
 * @param showDetailedResults Whether to show detailed results.
 *
 * @author Nicholas Rutherford
 */
data class QuizResultState(
    val quizTitle: String = "",
    val quizImageUrl: String = "",
    val resultText: String = "",
    val questions: List<SavedQuestionItem> = emptyList(),
    val showDetailedResults: Boolean = false
)
