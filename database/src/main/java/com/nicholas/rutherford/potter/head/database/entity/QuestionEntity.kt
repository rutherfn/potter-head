package com.nicholas.rutherford.potter.head.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Entity representing a question apart of [QuizEntity] in the Room database.
 * The question id serves as the primary key.
 *
 * @property id The unique identifier of the question.
 * @property text The text content of the question.
 * @property answers A list of [AnswerEntity] representing the possible answers to the question.
 *
 * @author Nicholas Rutherford
 */
@Entity("questions")
data class QuestionEntity(
    @PrimaryKey
    val id: String,
    val text: String,
    val answers: List<AnswerEntity>
)
