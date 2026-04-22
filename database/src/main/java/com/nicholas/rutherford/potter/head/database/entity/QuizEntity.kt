package com.nicholas.rutherford.potter.head.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Entity representing a quiz in the Room database.
 * The quizzes id serves as the primary key.
 *
 * @property id The unique identifier of the quiz.
 * @property title The title of the quiz.
 * @property description The description of the quiz.
 * @property longDescription The long description of the quiz.
 * @property quizImageUrl The URL of the quiz image.
 * @property resultsInfo Metadata per possible outcome; never null (empty when JSON has no entries).
 *   Auto-migration fills existing rows with an empty JSON array via [ColumnInfo.defaultValue].
 * @property results A list of strings representing the results of the quiz.
 * @property questions A list of [QuestionEntity] representing the questions in the quiz.
 *
 * @author Nicholas Rutherford
 */
@Entity(tableName = "quizzes")
data class QuizEntity(
    @PrimaryKey
    val id: String,
    val title: String,
    val description: String,
    val longDescription: String,
    val quizImageUrl: String,
    @ColumnInfo(defaultValue = "'[]'")
    val resultsInfo: List<ResultsInfoEntity>,
    val results: List<String>,
    val questions: List<QuestionEntity>
)
