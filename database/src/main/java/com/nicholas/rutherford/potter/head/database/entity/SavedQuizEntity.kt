package com.nicholas.rutherford.potter.head.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Entity representing a completed quiz attempt saved by the user.
 * Stores quiz metadata and full question/answer data so results can be shown from this repo alone.
 *
 * @property id Unique row id for this saved attempt; auto-generated on insert ([androidx.room.PrimaryKey.autoGenerate]).
 * @property quizId The id of the quiz template this was taken from.
 * @property quizTitle Quiz title for display.
 * @property quizDescription Quiz description for display.
 * @property quizImageUrl Quiz image URL for display.
 * @property resultText The result the user got (e.g. "Gryffindor").
 * @property resultImageUrl The URL of the image associated with the result.
 * @property resultMoreInfo Additional information about the result.
 * @property savedAt Timestamp when the quiz was saved (e.g. System.currentTimeMillis()).
 * @property questions Questions with possible answers and correct answer, stored as JSON.
 *
 * @author Nicholas Rutherford
 */
@Entity(tableName = "savedQuizzes")
data class SavedQuizEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val quizId: String,
    val quizTitle: String,
    val quizDescription: String,
    val quizImageUrl: String,
    val resultText: String,
    val resultImageUrl: String,
    val resultMoreInfo: String,
    val savedAt: Long,
    val questions: List<SavedQuestionItem>
)
