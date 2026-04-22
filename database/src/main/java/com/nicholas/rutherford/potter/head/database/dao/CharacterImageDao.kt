package com.nicholas.rutherford.potter.head.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.nicholas.rutherford.potter.head.database.entity.CharacterImageUrlEntity
import kotlinx.coroutines.flow.Flow


/**
 * Data Access Object for CharacterImageUrlEntity.
 * Provides methods to interact with the character urls table in the Room database.
 *
 * @author Nicholas Rutherford
 */
@Dao
interface CharacterImageDao {
    @Query("SELECT * FROM characterUrls")
    fun getAllCharacterImageUrls(): Flow<List<CharacterImageUrlEntity>>

    @Query("SELECT * FROM characterUrls")
    suspend fun getAllCharacterImageUrlsSync(): List<CharacterImageUrlEntity>

    @Query("SELECT * FROM characterUrls WHERE id = :id")
    fun getCharacterImageUrlById(id: Int): Flow<CharacterImageUrlEntity?>

    @Query("SELECT * FROM characterUrls WHERE LOWER(TRIM(characterName)) = LOWER(TRIM(:characterName))")
    fun getCharacterImageUrlByName(characterName: String): Flow<CharacterImageUrlEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllCharacterImageUrls(characterImageUrls: List<CharacterImageUrlEntity>)

    @Query("DELETE FROM characterUrls WHERE id = :id")
    suspend fun deleteCharacterImageUrlById(id: Int)

    @Query("DELETE FROM characterUrls")
    suspend fun deleteAllCharacterImageUrls()
}
