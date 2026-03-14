package com.nicholas.rutherford.potter.head.core.jsonresponse

/**
 * Data class representing the JSON response for answers field found in [QuizQuestionJsonResponse].
 *
 * @param text The text content of the answer.
 * @param points A map of result identifiers to points associated with that answer.
 *               Can be null for trivia quizzes that use "isCorrect" instead.
 * @param isCorrect Boolean indicating if the answer is correct (used for trivia quizzes).
 *                  Can be null for personality quizzes that use "points" instead.
 *
 * @author Nicholas Rutherford
 */
data class QuizAnswerJsonResponse(
    val text: String,
    val points: Map<String, Int>? = null,
    val isCorrect: Boolean? = null
)