package com.nicholas.rutherford.potter.head.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Entity representing a completed quiz attempt saved by the user.
 * Stores quiz metadata and full question/answer data so results can be shown from this repo alone.
 *
 * @property id Unique identifier for this saved attempt (e.g. list size + 1 when saving).
 * @property quizId The id of the quiz template this was taken from.
 * @property quizTitle Quiz title for display.
 * @property quizDescription Quiz description for display.
 * @property quizImageUrl Quiz image URL for display.
 * @property resultText The result the user got (e.g. "Gryffindor").
 * @property savedAt Timestamp when the quiz was saved (e.g. System.currentTimeMillis()).
 * @property questions Questions with possible answers and correct answer, stored as JSON.
 *
 * @author Nicholas Rutherford
 */
@Entity(tableName = "savedQuizzes")
data class SavedQuizEntity(
    @PrimaryKey
    val id: Long,
    val quizId: String,
    val quizTitle: String,
    val quizDescription: String,
    val quizImageUrl: String,
    val resultText: String,
    val savedAt: Long,
    val questions: List<SavedQuestionItem>
)
