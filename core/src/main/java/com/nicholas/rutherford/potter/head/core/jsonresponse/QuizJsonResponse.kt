package com.nicholas.rutherford.potter.head.core.jsonresponse

/**
 * Data class representing the JSON response for quiz.
 *
 * @param id The unique identifier of the quiz.
 * @param title The title of the quiz.
 * @param description The description of the quiz.
 * @param longDescription The long description of the quiz.
 * @param quizImageUrl The URL of the quiz image.
 * @param results A list of strings representing the results of the quiz.
 * @param questions A list of [QuizQuestionJsonResponse] representing the questions in the quiz.
 *
 * @author Nicholas Rutherford
 */
data class QuizJsonResponse(
    val id: String,
    val title: String,
    val description: String,
    val longDescription: String,
    val quizImageUrl: String,
    val results: List<String>,
    val questions: List<QuizQuestionJsonResponse>
)