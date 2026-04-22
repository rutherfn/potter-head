package com.nicholas.rutherford.potter.head.database.repository

import com.nicholas.rutherford.potter.head.database.converter.QuizConverter
import com.nicholas.rutherford.potter.head.database.converter.SavedQuizConverter
import com.nicholas.rutherford.potter.head.database.dao.SavedQuizDao
import com.nicholas.rutherford.potter.head.database.entity.AnswerEntity

/**
 * Implementation of [SavedQuizRepository].
 * Handles conversion between entities and converters.
 *
 * @param dao The DAO for accessing saved quizzes.
 *
 * @author Nicholas Rutherford
 */
class SavedQuizRepositoryImpl(private val dao: SavedQuizDao) : SavedQuizRepository {

    override fun getAllSavedQuizzes(): List<SavedQuizConverter> {
        return dao.getAllSavedQuizzes().map { entity ->
            SavedQuizConverter.fromEntity(entity = entity)
        }
    }

    override fun getSavedQuizById(id: Long): SavedQuizConverter? {
        return dao.getSavedQuizById(id = id)?.let { entity ->
            SavedQuizConverter.fromEntity(entity = entity)
        }
    }

    override suspend fun getAllSavedQuizzesCount(): Int = dao.getSavedQuizCount()

    override suspend fun insertQuiz(
        quiz: QuizConverter,
        resultText: String,
        resultImageUrl: String,
        resultMoreInfo: String,
        selectedAnswers: List<AnswerEntity>
    ): Long {
        val converter = SavedQuizConverter.fromQuizAndResult(
            quiz = quiz,
            resultText = resultText,
            resultImageUrl = resultImageUrl,
            resultMoreInfo = resultMoreInfo,
            id = 0L,
            selectedAnswers = selectedAnswers
        )
        return dao.insertSavedQuiz(savedQuiz = converter.toEntity())
    }

    override suspend fun deleteSavedQuizById(id: Long) = dao.deleteSavedQuizById(id = id)
}
