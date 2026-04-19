package com.nicholas.rutherford.potter.head.core.jsonresponse

/**
 * Data class representing the JSON response for answers field found in [QuizJsonResponse].
 *
 * @param id The unique identifier of the quiz question.
 * @param text The text content of the quiz question.
 * @param answers A list of [QuizAnswerJsonResponse] representing the possible answers to the question.
 *
 * @author Nicholas Rutherford
 */
data class QuizQuestionJsonResponse(
    val id: String,
    val text: String,
    val answers: List<QuizAnswerJsonResponse>
)
