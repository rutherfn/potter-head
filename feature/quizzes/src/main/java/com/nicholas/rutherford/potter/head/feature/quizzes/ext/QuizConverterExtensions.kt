package com.nicholas.rutherford.potter.head.feature.quizzes.ext

import com.nicholas.rutherford.potter.head.database.converter.QuizConverter
import com.nicholas.rutherford.potter.head.database.converter.SavedQuizConverter
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

private fun String.quizTemplateIdToQuizType(): QuizType = when (lowercase()) {
    "sorting_hat" -> QuizType.SORTING_HAT
    "wand", "wand_quiz" -> QuizType.WAND
    "patronus", "patronus_quiz" -> QuizType.PATRONUS
    "character", "character_quiz" -> QuizType.CHARACTER
    "trivia", "trivia_quiz" -> QuizType.TRIVIA
    else -> QuizType.NONE
}

/** Converts a timestamp in milliseconds to a formatted string with date and time using "MMM dd, yyyy" format. */
fun Long.toDateWithTimestampString(): String {
    val formatter = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault())
    return formatter.format(Date(this))
}

/**
 * Extension function to convert a database [QuizConverter] to a feature [QuizzesConverter].
 *
 * @return A [QuizzesConverter] with the quiz data formatted for UI display.
 *
 * @author Nicholas Rutherford
 */
fun QuizConverter.toQuizzesConverter(): QuizzesConverter = QuizzesConverter(
    id = 0L, // not used only used for saved quiz converter
    title = title,
    description = description,
    longDescription = longDescription,
    imageUrl = quizImageUrl,
    type = id.quizTemplateIdToQuizType(),
    timestampOfLastLogged = null,
    quizResult = null
)

/**
 * Extension function to convert a database [SavedQuizConverter] to a feature [QuizzesConverter].
 *
 * @return A [QuizzesConverter] with the quiz data formatted for UI display.
 *
 * @author Nicholas Rutherford
 */
fun SavedQuizConverter.toQuizzesConverter(): QuizzesConverter = QuizzesConverter(
    id = id,
    title = quizTitle,
    description = quizDescription,
    longDescription = resultMoreInfo.ifBlank { quizDescription },
    imageUrl = resultImageUrl.ifBlank { quizImageUrl },
    type = quizId.quizTemplateIdToQuizType(),
    timestampOfLastLogged = savedAt.toDateWithTimestampString(),
    quizResult = resultText
)
