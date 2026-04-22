package com.nicholas.rutherford.potter.head.database

import android.content.Context
import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.nicholas.rutherford.potter.head.core.Constants
import com.nicholas.rutherford.potter.head.database.dao.CharacterDao
import com.nicholas.rutherford.potter.head.database.dao.CharacterFilterDao
import com.nicholas.rutherford.potter.head.database.dao.CharacterImageDao
import com.nicholas.rutherford.potter.head.database.dao.DebugToggleDao
import com.nicholas.rutherford.potter.head.database.dao.QuizDao
import com.nicholas.rutherford.potter.head.database.dao.SavedQuizDao
import com.nicholas.rutherford.potter.head.database.dao.SpellDao
import com.nicholas.rutherford.potter.head.database.entity.CharacterEntity
import com.nicholas.rutherford.potter.head.database.entity.CharacterFilterEntity
import com.nicholas.rutherford.potter.head.database.entity.CharacterImageUrlEntity
import com.nicholas.rutherford.potter.head.database.entity.DebugToggleEntity
import com.nicholas.rutherford.potter.head.database.entity.QuizEntity
import com.nicholas.rutherford.potter.head.database.entity.SavedQuizEntity
import com.nicholas.rutherford.potter.head.database.entity.SpellEntity
import com.nicholas.rutherford.potter.head.database.typeconverter.DatabaseTypeConverters

/**
 * Room database for the application.
 * Handles database creation, migrations, and provides access to DAOs.
 *
 * @author Nicholas Rutherford
 */
@Database(
    entities = [
        CharacterEntity::class,
        CharacterFilterEntity::class,
        CharacterImageUrlEntity::class,
        DebugToggleEntity::class,
        SpellEntity::class,
        QuizEntity::class,
        SavedQuizEntity::class
               ],
    autoMigrations = [
        AutoMigration(from = 1, to = 2),
        AutoMigration(from = 2, to = 3),
        AutoMigration(from = 3, to = 4),
        AutoMigration(from = 4, to = 5),
        AutoMigration(from = 5, to = 6),
        AutoMigration(from = 6, to = 7),
        AutoMigration(from = 7, to = 8),
        AutoMigration(from = 8, to = 9),
        AutoMigration(from = 9, to = 10)
    ],
    version = 10,
    exportSchema = true
)
@TypeConverters(DatabaseTypeConverters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun debugToggleDao(): DebugToggleDao
    abstract fun characterFilterDao(): CharacterFilterDao
    abstract fun characterDao(): CharacterDao
    abstract fun spellDao(): SpellDao
    abstract fun characterImageDao(): CharacterImageDao
    abstract fun quizDao(): QuizDao
    abstract fun savedQuizDao(): SavedQuizDao

    companion object {

        fun create(context: Context): AppDatabase {
            return Room.databaseBuilder(
                context,
                AppDatabase::class.java,
                Constants.DATABASE_NAME
            ).build()
        }
    }
}
