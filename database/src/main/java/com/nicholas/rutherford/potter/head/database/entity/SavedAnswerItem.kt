package com.nicholas.rutherford.potter.head.database.entity

/**
 * Represents a single answer option within a saved quiz question.
 * Stored as part of [SavedQuestionItem] in the database via TypeConverter.
 *
 * @property text The answer option text.
 * @property isCorrect Whether this answer is the correct one (trivia); false when not applicable.
 * @property isSelected Whether the user chose this option for this attempt.
 *
 * @author Nicholas Rutherford
 */
data class SavedAnswerItem(
    val text: String,
    val isCorrect: Boolean,
    val isSelected: Boolean = false
)
