package com.nicholas.rutherford.potter.head.database.repository

import com.nicholas.rutherford.potter.head.database.converter.QuizConverter
import com.nicholas.rutherford.potter.head.database.converter.SavedQuizConverter
import com.nicholas.rutherford.potter.head.database.dao.SavedQuizDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * Implementation of [SavedQuizRepository].
 * Handles conversion between entities and converters.
 *
 * @param dao The DAO for accessing saved quizzes.
 *
 * @author Nicholas Rutherford
 */
class SavedQuizRepositoryImpl(private val dao: SavedQuizDao) : SavedQuizRepository {

    override fun getAllSavedQuizzes(): Flow<List<SavedQuizConverter>> {
        return dao.getAllSavedQuizzes().map { entities ->
            entities.map { entity -> SavedQuizConverter.fromEntity(entity = entity) }
        }
    }

    override fun getSavedQuizById(id: Long): Flow<SavedQuizConverter?> {
        return dao.getSavedQuizById(id).map { entity ->
            entity?.let { value -> SavedQuizConverter.fromEntity(entity = value) }
        }
    }

    override suspend fun insertQuiz(quiz: QuizConverter, resultText: String) {
        val converter = SavedQuizConverter.fromQuizAndResult(
            quiz = quiz,
            resultText = resultText,
            id = dao.getSavedQuizCount() + 1L
        )
        dao.insertSavedQuiz(savedQuiz = converter.toEntity())
    }

    override suspend fun deleteSavedQuizById(id: Long) = dao.deleteSavedQuizById(id = id)
}
