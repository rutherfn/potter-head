package com.nicholas.rutherford.potter.head.core.jsonresponse

/**
 * Data class representing the JSON response for answers field found in [QuizQuestionJsonResponse].
 *
 * @param text The text content of the answer.
 * @param points A map of result identifiers to points associated with that answer.
 *
 * @author Nicholas Rutherford
 */
data class QuizAnswerJsonResponse(
    val text: String,
    val points: Map<String, Int>
)