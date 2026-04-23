package com.nicholas.rutherford.potter.head.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.nicholas.rutherford.potter.head.database.entity.SavedQuizEntity

/**
 * Data Access Object for [SavedQuizEntity].
 * Provides methods to interact with the saved_quizzes table in the Room database.
 *
 * @author Nicholas Rutherford
 */
@Dao
interface SavedQuizDao {
    @Query("SELECT COUNT(*) FROM savedQuizzes")
    suspend fun getSavedQuizCount(): Int

    @Query("SELECT * FROM savedQuizzes ORDER BY savedAt DESC")
    suspend fun getAllSavedQuizzes(): List<SavedQuizEntity>

    @Query("SELECT * FROM savedQuizzes WHERE id = :id")
    suspend fun getSavedQuizById(id: Long): SavedQuizEntity?

    @Insert
    suspend fun insertSavedQuiz(savedQuiz: SavedQuizEntity): Long

    @Query("DELETE FROM savedQuizzes WHERE id = :id")
    suspend fun deleteSavedQuizById(id: Long)
}
