package com.nicholas.rutherford.potter.head.database.repository

import android.content.Context
import com.nicholas.rutherford.potter.head.core.JsonReader
import com.nicholas.rutherford.potter.head.database.converter.QuizConverter
import com.nicholas.rutherford.potter.head.database.dao.QuizDao

/**
 * Implementation of QuizRepository.
 * Handles the conversion between entities and converters.
 *
 * @param dao The DAO for accessing quizzes.
 * @param context The application context used to call [JsonReader]
 *
 * @author Nicholas Rutherford
 */
class QuizRepositoryImpl(
    private val dao: QuizDao,
    private val context: Context
) : QuizRepository {

    override suspend fun getQuizCount(): Int = dao.getQuizCount()

    override suspend fun getAllQuizzes(): List<QuizConverter> {
        return dao.getAllQuizzes().map { entity -> QuizConverter.fromEntity(entity = entity) }
    }

    override suspend fun getQuizById(id: String): QuizConverter? {
        return dao.getQuizById(id)?.let { entity -> QuizConverter.fromEntity(entity = entity) }
    }

    override suspend fun getQuizByTitle(title: String): QuizConverter? {
        return dao.getQuizByTitle(title = title)?.let { entity -> QuizConverter.fromEntity(entity = entity) }
    }

    override suspend fun insertQuiz(quiz: QuizConverter) {
        dao.insertQuiz(quiz = quiz.toEntity())
    }

    override suspend fun insertAllQuizzes(quizzes: List<QuizConverter>) {
        dao.insertAllQuizzes(quizzes = quizzes.map { quiz -> quiz.toEntity() })
    }

    override suspend fun insertAllQuizzesFromJson() {
        val quizzesJson = JsonReader.getQuizzes(context = context)
        val quizConverters = quizzesJson.map { jsonResponse -> QuizConverter.fromJsonResponse(response = jsonResponse) }
        
        dao.insertAllQuizzes(quizzes = quizConverters.map { converter -> converter.toEntity() })
    }

    override suspend fun updateQuiz(quiz: QuizConverter) {
        dao.updateQuiz(quiz = quiz.toEntity())
    }

    override suspend fun deleteQuizById(id: String) {
        dao.deleteQuizById(id = id)
    }

    override suspend fun deleteAllQuizzes() {
        dao.deleteAllQuizzes()
    }
}

