package com.nicholas.rutherford.potter.head.database.converter

import com.nicholas.rutherford.potter.head.core.jsonresponse.QuizJsonResponse
import com.nicholas.rutherford.potter.head.database.entity.AnswerEntity
import com.nicholas.rutherford.potter.head.database.entity.QuestionEntity
import com.nicholas.rutherford.potter.head.database.entity.QuizEntity

/**
 * Converter data class for QuizEntity.
 * Used to convert between entity and domain model representations.
 *
 * @property id The unique identifier of the quiz.
 * @property title The title of the quiz.
 * @property description The description of the quiz.
 * @property longDescription The long description of the quiz.
 * @property quizImageUrl The URL of the quiz image.
 * @property results A list of strings representing the results of the quiz.
 * @property questions A list of [QuestionEntity] representing the questions in the quiz.
 *
 * @author Nicholas Rutherford
 */
data class QuizConverter(
    val id: String,
    val title: String,
    val description: String,
    val longDescription: String,
    val quizImageUrl: String,
    val results: List<String>,
    val questions: List<QuestionEntity>
) {

    /**
     * Converts this converter to a QuizEntity.
     */
    fun toEntity(): QuizEntity = QuizEntity(
        id = id,
        title = title,
        description = description,
        longDescription = longDescription,
        quizImageUrl = quizImageUrl,
        results = results,
        questions = questions
    )

    companion object {

        /**
         * Creates a QuizConverter from a QuizEntity.
         */
        fun fromEntity(entity: QuizEntity): QuizConverter = QuizConverter(
            id = entity.id,
            title = entity.title,
            description = entity.description,
            longDescription = entity.longDescription,
            quizImageUrl = entity.quizImageUrl,
            results = entity.results,
            questions = entity.questions
        )

        /**
         * Creates a QuizConverter from a QuizJsonResponse.
         */
        fun fromJsonResponse(response: QuizJsonResponse): QuizConverter {
            val questions = response.questions.map { questionResponse ->
                QuestionEntity(
                    id = questionResponse.id,
                    text = questionResponse.text,
                    answers = questionResponse.answers.map { answerResponse ->
                        AnswerEntity(
                            text = answerResponse.text,
                            points = answerResponse.points,
                            isCorrect = answerResponse.isCorrect
                        )
                    }
                )
            }

            return QuizConverter(
                id = response.id,
                title = response.title,
                description = response.description,
                longDescription = response.longDescription,
                quizImageUrl = response.quizImageUrl,
                results = response.results,
                questions = questions
            )
        }
    }
}

