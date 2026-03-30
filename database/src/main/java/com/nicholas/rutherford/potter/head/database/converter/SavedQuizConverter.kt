package com.nicholas.rutherford.potter.head.database.converter

import com.nicholas.rutherford.potter.head.database.entity.AnswerEntity
import com.nicholas.rutherford.potter.head.database.entity.SavedAnswerItem
import com.nicholas.rutherford.potter.head.database.entity.SavedQuestionItem
import com.nicholas.rutherford.potter.head.database.entity.SavedQuizEntity

/**
 * Converter data class for [SavedQuizEntity].
 * Used to convert between entity and domain model representations.
 *
 * @property id Unique identifier for this saved attempt.
 * @property quizId The id of the quiz template this was taken from.
 * @property quizTitle Quiz title for display.
 * @property quizDescription Quiz description for display.
 * @property quizImageUrl Quiz image URL for display.
 * @property resultText The result the user got (e.g. "Gryffindor").
 * @property savedAt Timestamp when the quiz was saved.
 * @property questions Questions with possible answers and correct answer.
 *
 * @author Nicholas Rutherford
 */
data class SavedQuizConverter(
    val id: Long,
    val quizId: String,
    val quizTitle: String,
    val quizDescription: String,
    val quizImageUrl: String,
    val resultText: String,
    val savedAt: Long,
    val questions: List<SavedQuestionItem>
) {

    /**
     * Converts this converter to a SavedQuizEntity.
     */
    fun toEntity(): SavedQuizEntity = SavedQuizEntity(
        id = id,
        quizId = quizId,
        quizTitle = quizTitle,
        quizDescription = quizDescription,
        quizImageUrl = quizImageUrl,
        resultText = resultText,
        savedAt = savedAt,
        questions = questions
    )

    companion object {

        /**
         * Creates a SavedQuizConverter from a SavedQuizEntity.
         */
        fun fromEntity(entity: SavedQuizEntity): SavedQuizConverter = SavedQuizConverter(
            id = entity.id,
            quizId = entity.quizId,
            quizTitle = entity.quizTitle,
            quizDescription = entity.quizDescription,
            quizImageUrl = entity.quizImageUrl,
            resultText = entity.resultText,
            savedAt = entity.savedAt,
            questions = entity.questions
        )

        /**
         * Maps a [QuizConverter] and the user's result text into a new [SavedQuizConverter].
         * Use when saving a completed quiz. Caller must provide [id] (e.g. saved quiz list size + 1).
         */
        fun fromQuizAndResult(
            quiz: QuizConverter,
            resultText: String,
            id: Long,
            selectedAnswers: List<AnswerEntity>,
            savedAt: Long = System.currentTimeMillis()
        ): SavedQuizConverter {
            val questions = quiz.questions.mapIndexed { index, question ->
                val selectedForQuestion = selectedAnswers.getOrNull(index)
                SavedQuestionItem(
                    questionText = question.text,
                    answers = question.answers.map { answer ->
                        SavedAnswerItem(
                            text = answer.text,
                            isCorrect = answer.isCorrect ?: false,
                            isSelected = selectedForQuestion != null && answer.text == selectedForQuestion.text
                        )
                    }
                )
            }
            return SavedQuizConverter(
                id = id,
                quizId = quiz.id,
                quizTitle = quiz.title,
                quizDescription = quiz.description,
                quizImageUrl = quiz.quizImageUrl,
                resultText = resultText,
                savedAt = savedAt,
                questions = questions
            )
        }
    }
}
