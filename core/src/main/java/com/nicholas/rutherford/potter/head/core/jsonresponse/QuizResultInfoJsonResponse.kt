package com.nicholas.rutherford.potter.head.core.jsonresponse

/**
 * Data class representing the JSON response for quiz result info.
 *
 * @param answer The answer result of completing the quiz.
 * @param moreInfo Additional information about the answer.
 * @param imageUrl The URL of the image related to the answer.
 *
 * @author Nicholas Rutherford
 */
data class QuizResultInfoJsonResponse(
    val answer: String,
    val moreInfo: String,
    val imageUrl: String
)