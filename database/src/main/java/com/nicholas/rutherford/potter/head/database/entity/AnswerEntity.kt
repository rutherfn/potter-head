package com.nicholas.rutherford.potter.head.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Entity representing an answer option for a quiz question in the Room database.
 * The answer text serves as the primary key.
 *
 * @property text The text content of the answer.
 * @property points A map of result identifiers to points associated with that answer.
 *                  The map is stored as JSON in the database via a TypeConverter.
 *                  Can be null for trivia quizzes that use "isCorrect" instead.
 * @property isCorrect Boolean indicating if the answer is correct (used for trivia quizzes).
 *                      Can be null for personality quizzes that use "points" instead.
 *
 * @author Nicholas Rutherford
 */
@Entity(tableName = "answers")
data class AnswerEntity(
    @PrimaryKey
    val text: String,
    val points: Map<String, Int>? = null,
    val isCorrect: Boolean? = null
)
