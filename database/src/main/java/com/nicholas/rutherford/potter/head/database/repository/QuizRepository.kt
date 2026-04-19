package com.nicholas.rutherford.potter.head.database.repository

import com.nicholas.rutherford.potter.head.database.converter.QuizConverter

/**
 * Repository interface for managing quizzes.
 * Provides a clean way to access and modify cached quizzes data in the database.
 *
 * @author Nicholas Rutherford
 */
interface QuizRepository {
    suspend fun getQuizCount(): Int

    suspend fun getAllQuizzes(): List<QuizConverter>

    suspend fun getQuizById(id: String): QuizConverter?

    suspend fun getQuizByTitle(title: String): QuizConverter?

    suspend fun insertQuiz(quiz: QuizConverter)

    suspend fun insertAllQuizzes(quizzes: List<QuizConverter>)

    suspend fun insertAllQuizzesFromJson()

    suspend fun updateQuiz(quiz: QuizConverter)

    suspend fun deleteQuizById(id: String)

    suspend fun deleteAllQuizzes()
}
