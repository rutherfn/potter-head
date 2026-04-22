package com.nicholas.rutherford.potter.head.feature.quizzes.ext

import com.nicholas.rutherford.potter.head.database.converter.QuizConverter

/**
 * Extension function to convert a database [QuizConverter] to a feature [QuizzesConverter].
 *
 * @return A [QuizzesConverter] with the quiz data formatted for UI display.
 *
 * @author Nicholas Rutherford
 */
fun QuizConverter.toQuizzesConverter(): QuizzesConverter {
    val quizType = when (id.lowercase()) {
        "sorting_hat" -> QuizType.SORTING_HAT
        "wand" -> QuizType.WAND
        "patronus" -> QuizType.PATRONUS
        "character" -> QuizType.CHARACTER
        "trivia" -> QuizType.TRIVIA
        else -> QuizType.NONE
    }

    return QuizzesConverter(
        title = title,
        description = description,
        longDescription = longDescription,
        imageUrl = quizImageUrl,
        type = quizType
    )
}
