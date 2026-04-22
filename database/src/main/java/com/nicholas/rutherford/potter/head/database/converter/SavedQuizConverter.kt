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
 * @property resultImageUrl The URL of the image associated with the result.
 * @property resultMoreInfo Additional information about the result.
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
    val resultImageUrl: String,
    val resultMoreInfo: String,
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
        resultImageUrl = resultImageUrl,
        resultMoreInfo = resultMoreInfo,
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
            resultImageUrl = entity.resultImageUrl,
            resultMoreInfo = entity.resultMoreInfo,
            savedAt = entity.savedAt,
            questions = entity.questions
        )

        /**
         * Creates a [SavedQuizConverter] from a [QuizConverter], result presentation fields, and the
         * answers the user selected. Questions are mapped in order; each [SavedAnswerItem.isSelected]
         * is true when its text matches the selected answer for that question index.
         *
         * @param quiz The quiz template that was taken.
         * @param resultText Result for the outcome of the result of quiz (e.g. house name or tier).
         * @param resultImageUrl URL of the image shown for this result.
         * @param resultMoreInfo Longer description or details for the result.
         * @param id Row id; use `0` for new rows so Room assigns [androidx.room.PrimaryKey.autoGenerate].
         * @param selectedAnswers Choices in the same order as [quiz.questions]; one entry per question answered.
         * @param savedAt Epoch milliseconds when the attempt was saved; defaults to the current time.
         *
         * @return A converter ready to persist with [toEntity].
         */
        fun fromQuizAndResult(
            quiz: QuizConverter,
            resultText: String,
            resultImageUrl: String,
            resultMoreInfo: String,
            id: Long = 0L,
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
                resultImageUrl = resultImageUrl,
                resultMoreInfo = resultMoreInfo,
                savedAt = savedAt,
                questions = questions
            )
        }
    }
}
