package com.nicholas.rutherford.potter.head.database.repository

import com.nicholas.rutherford.potter.head.database.converter.QuizConverter
import com.nicholas.rutherford.potter.head.database.converter.SavedQuizConverter
import kotlinx.coroutines.flow.Flow

/**
 * Repository interface for managing saved (completed) quizzes.
 * Provides access to saved quiz results so the UI can show them from this repo alone.
 *
 * @author Nicholas Rutherford
 */
interface SavedQuizRepository {
    fun getAllSavedQuizzes(): Flow<List<SavedQuizConverter>>

    fun getSavedQuizById(id: Long): Flow<SavedQuizConverter?>

    suspend fun insertQuiz(quiz: QuizConverter, resultText: String)

    suspend fun deleteSavedQuizById(id: Long)
}
