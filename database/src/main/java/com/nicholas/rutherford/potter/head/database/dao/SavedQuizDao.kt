package com.nicholas.rutherford.potter.head.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.nicholas.rutherford.potter.head.database.entity.SavedQuizEntity
import kotlinx.coroutines.flow.Flow

/**
 * Data Access Object for [SavedQuizEntity].
 * Provides methods to interact with the saved_quizzes table in the Room database.
 *
 * @author Nicholas Rutherford
 */
@Dao
interface SavedQuizDao {
    @Query("SELECT COUNT(*) FROM saved_quizzes")
    suspend fun getSavedQuizCount(): Int

    @Query("SELECT * FROM saved_quizzes ORDER BY savedAt DESC")
    fun getAllSavedQuizzes(): Flow<List<SavedQuizEntity>>

    @Query("SELECT * FROM saved_quizzes WHERE id = :id")
    fun getSavedQuizById(id: Long): Flow<SavedQuizEntity?>

    @Insert
    suspend fun insertSavedQuiz(savedQuiz: SavedQuizEntity): Long

    @Query("DELETE FROM saved_quizzes WHERE id = :id")
    suspend fun deleteSavedQuizById(id: Long)
}
