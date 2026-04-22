package com.nicholas.rutherford.potter.head.database.repository

import com.nicholas.rutherford.potter.head.database.converter.QuizConverter
import com.nicholas.rutherford.potter.head.database.converter.SavedQuizConverter
import com.nicholas.rutherford.potter.head.database.entity.AnswerEntity

/**
 * Repository interface for managing saved (completed) quizzes.
 * Provides access to saved quiz results so the UI can show them from this repo alone.
 *
 * @author Nicholas Rutherford
 */
interface SavedQuizRepository {
    suspend fun getAllSavedQuizzes(): List<SavedQuizConverter>

    suspend fun getSavedQuizById(id: Long): SavedQuizConverter?

    suspend fun getAllSavedQuizzesCount(): Int
    suspend fun insertQuiz(
        quiz: QuizConverter,
        resultText: String,
        resultImageUrl: String,
        resultMoreInfo: String,
        selectedAnswers: List<AnswerEntity>
    ): Long

    suspend fun deleteSavedQuizById(id: Long)
}
