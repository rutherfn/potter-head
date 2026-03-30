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
    fun getAllSavedQuizzes(): List<SavedQuizConverter>

    fun getSavedQuizById(id: Long): SavedQuizConverter?

    suspend fun getAllSavedQuizzesCount(): Int
    suspend fun insertQuiz(quiz: QuizConverter, resultText: String, selectedAnswers: List<AnswerEntity>)

    suspend fun deleteSavedQuizById(id: Long)
}
