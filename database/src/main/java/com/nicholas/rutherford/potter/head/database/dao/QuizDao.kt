package com.nicholas.rutherford.potter.head.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.nicholas.rutherford.potter.head.database.entity.QuizEntity
import kotlinx.coroutines.flow.Flow

/**
 * Data Access Object for QuizEntity.
 * Provides methods to interact with the quizzes table in the Room database.
 *
 * @author Nicholas Rutherford
 */
@Dao
interface QuizDao {
    @Query("SELECT COUNT(*) FROM quizzes")
    suspend fun getQuizCount(): Int

    @Query("SELECT * FROM quizzes")
    fun getAllQuizzes(): Flow<List<QuizEntity>>

    @Query("SELECT * FROM quizzes WHERE id = :id")
    fun getQuizById(id: String): Flow<QuizEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertQuiz(quiz: QuizEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllQuizzes(quizzes: List<QuizEntity>)

    @Update
    suspend fun updateQuiz(quiz: QuizEntity)

    @Query("DELETE FROM quizzes WHERE id = :id")
    suspend fun deleteQuizById(id: String)

    @Query("DELETE FROM quizzes")
    suspend fun deleteAllQuizzes()
}


