package com.nicholas.rutherford.potter.head.feature.quizzes.takequiz

import com.nicholas.rutherford.potter.head.database.converter.QuizConverter
import com.nicholas.rutherford.potter.head.database.entity.AnswerEntity

/**
 * Outcome text and optional rich result metadata for a completed quiz.
 */
data class QuizOutcome(
    val resultText: String,
    val resultImageUrl: String,
    val resultMoreInfo: String
)

/**
 * Computes the quiz result from [selectedAnswers] and the quiz definition.
 *
 * Personality-style quizzes sum [AnswerEntity.points] per outcome key and pick the highest score
 * (ties broken by order in [QuizConverter.resultsInfo] then [QuizConverter.results]).
 *
 * Trivia-style quizzes (answers use [AnswerEntity.isCorrect]) map the number of correct answers
 * into bands across [QuizConverter.results] (lowest tier for fewest correct, highest for most).
 */
object QuizOutcomeResolver {

    fun resolve(quiz: QuizConverter, selectedAnswers: List<AnswerEntity>): QuizOutcome {
        val aligned = alignSelectedWithTemplate(quiz = quiz, selectedAnswers = selectedAnswers)
        val hasPointsSelections = aligned.any { !it.points.isNullOrEmpty() }
        val isStrictTrivia = aligned.isNotEmpty() &&
            aligned.all { it.points.isNullOrEmpty() && it.isCorrect != null }

        return when {
            hasPointsSelections ->
                resolvePersonality(quiz = quiz, selectedAnswers = aligned)

            isStrictTrivia ->
                resolveTrivia(quiz = quiz, selectedAnswers = aligned)

            else -> fallbackOutcome(quiz = quiz)
        }
    }

    /**
     * Rebinds each chosen answer to the matching option on [quiz.questions] (same index, same [AnswerEntity.text]).
     * Ensures scoring uses the template's [AnswerEntity.points] / [AnswerEntity.isCorrect] even if a stale copy
     * of the answer was held elsewhere.
     */
    private fun alignSelectedWithTemplate(quiz: QuizConverter, selectedAnswers: List<AnswerEntity>): List<AnswerEntity> {
        if (selectedAnswers.isEmpty()) return selectedAnswers
        return selectedAnswers.mapIndexed { index, chosen ->
            val question = quiz.questions.getOrNull(index)
            question?.answers?.firstOrNull { it.text == chosen.text } ?: chosen
        }
    }

    internal fun normalizeResultKey(raw: String): String =
        raw.lowercase().trim().replace(" ", "_")

    private fun resolvePersonality(quiz: QuizConverter, selectedAnswers: List<AnswerEntity>): QuizOutcome {
        val totals = mutableMapOf<String, Int>()
        selectedAnswers.forEach { answer ->
            answer.points?.forEach { (key, value) ->
                totals[key] = (totals[key] ?: 0) + value
            }
        }
        if (totals.isEmpty()) return fallbackOutcome(quiz = quiz)

        val maxScore = totals.values.maxOrNull() ?: return fallbackOutcome(quiz = quiz)
        val tiedKeys = totals.filter { it.value == maxScore }.keys
        val winningKey = pickWinningKeyAmongTies(tiedKeys = tiedKeys, quiz = quiz)

        val normalizedWin = normalizeResultKey(winningKey)
        val info = quiz.resultsInfo.firstOrNull { normalizeResultKey(it.answer) == normalizedWin }
        if (info != null) {
            return QuizOutcome(
                resultText = info.answer,
                resultImageUrl = info.imageUrl,
                resultMoreInfo = info.moreInfo
            )
        }
        val fromResults = quiz.results.firstOrNull { result ->
            normalizeResultKey(result) == normalizedWin ||
                result.equals(winningKey, ignoreCase = true)
        }
        return QuizOutcome(
            resultText = fromResults ?: winningKey,
            resultImageUrl = "",
            resultMoreInfo = ""
        )
    }

    private fun buildTiebreakOrder(quiz: QuizConverter): List<String> {
        return buildList {
            quiz.resultsInfo.forEach { add(normalizeResultKey(it.answer)) }
            quiz.results.forEach { add(normalizeResultKey(it)) }
        }.distinct()
    }

    private fun pickWinningKeyAmongTies(tiedKeys: Set<String>, quiz: QuizConverter): String {
        if (tiedKeys.size == 1) return tiedKeys.first()
        val order = buildTiebreakOrder(quiz = quiz)
        for (normalized in order) {
            tiedKeys.firstOrNull { candidate -> normalizeResultKey(candidate) == normalized }?.let { return it }
        }
        return tiedKeys.minOrNull() ?: tiedKeys.first()
    }

    private fun resolveTrivia(quiz: QuizConverter, selectedAnswers: List<AnswerEntity>): QuizOutcome {
        if (quiz.results.isEmpty()) return fallbackOutcome(quiz = quiz)
        val totalQuestions = selectedAnswers.size.coerceAtLeast(minimumValue = 1)
        val correctCount = selectedAnswers.count { it.isCorrect == true }
        val tierIndex = (correctCount * quiz.results.size / totalQuestions).coerceAtMost(quiz.results.lastIndex)
        val label = quiz.results[tierIndex]
        return QuizOutcome(resultText = label, resultImageUrl = "", resultMoreInfo = "")
    }

    private fun fallbackOutcome(quiz: QuizConverter): QuizOutcome {
        val info = quiz.resultsInfo.firstOrNull()
        if (info != null) {
            return QuizOutcome(
                resultText = info.answer,
                resultImageUrl = info.imageUrl,
                resultMoreInfo = info.moreInfo
            )
        }
        val r = quiz.results.firstOrNull().orEmpty()
        return QuizOutcome(resultText = r, resultImageUrl = "", resultMoreInfo = "")
    }
}
