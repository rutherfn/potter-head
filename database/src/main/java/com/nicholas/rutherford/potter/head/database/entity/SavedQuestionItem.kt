package com.nicholas.rutherford.potter.head.database.entity

/**
 * Represents a single question with its possible answers and correct answer.
 * Stored as part of [SavedQuizEntity] in the database via TypeConverter.
 *
 * @property questionText The question text.
 * @property answers All possible answers for this question; [SavedAnswerItem.isCorrect] marks the
 *                   correct option (trivia), and [SavedAnswerItem.isSelected] marks the user's choice.
 *
 * @author Nicholas Rutherford
 */
data class SavedQuestionItem(
    val questionText: String,
    val answers: List<SavedAnswerItem>
)
