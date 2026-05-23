package com.nicholas.rutherford.potter.head.feature.settings.debug

import com.nicholas.rutherford.potter.head.database.converter.QuizConverter
import com.nicholas.rutherford.potter.head.database.entity.ResultsInfoEntity

data class QuizResultUrlSection(
    val quizId: String,
    val header: QuizResultUrlItemParams,
    val resultItems: List<QuizResultUrlItemParams>,
) {
    companion object {
        fun fromQuizzes(quizzes: List<QuizConverter>): List<QuizResultUrlSection> {
            return quizzes.map { quiz -> from(quiz = quiz) }
        }

        fun from(quiz: QuizConverter): QuizResultUrlSection {
            return QuizResultUrlSection(
                quizId = quiz.id,
                header = toQuizHeaderItemParams(quiz = quiz),
                resultItems = quiz.resultsInfo.map { resultInfo ->
                    toQuizResultUrlItemParams(resultInfo = resultInfo, quiz = quiz)
                },
            )
        }

        private fun toQuizHeaderItemParams(quiz: QuizConverter): QuizResultUrlItemParams {
            return QuizResultUrlItemParams(
                imageUrl = quiz.quizImageUrl,
                fallbackImageUrl = "",
                resultLabel = quiz.title,
                emptyUrlMessage = "(no quiz image url — showing placeholder)",
            )
        }

        private fun toQuizResultUrlItemParams(
            resultInfo: ResultsInfoEntity,
            quiz: QuizConverter,
        ): QuizResultUrlItemParams {
            return QuizResultUrlItemParams(
                imageUrl = resultInfo.imageUrl,
                fallbackImageUrl = quiz.quizImageUrl,
                resultLabel = resultInfo.answer,
                emptyUrlMessage = "(no result url — showing placeholder or quiz image fallback)",
            )
        }
    }
}