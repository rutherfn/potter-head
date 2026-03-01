package com.nicholas.rutherford.potter.head.database

import android.content.Context
import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.nicholas.rutherford.potter.head.core.Constants
import com.nicholas.rutherford.potter.head.database.dao.CharacterDao
import com.nicholas.rutherford.potter.head.database.dao.CharacterImageDao
import com.nicholas.rutherford.potter.head.database.dao.DebugToggleDao
import com.nicholas.rutherford.potter.head.database.entity.CharacterEntity
import com.nicholas.rutherford.potter.head.database.entity.CharacterImageUrlEntity
import com.nicholas.rutherford.potter.head.database.entity.DebugToggleEntity
import com.nicholas.rutherford.potter.head.database.typeconverter.DatabaseTypeConverters

/**
 * Room database for the application.
 * Handles database creation, migrations, and provides access to DAOs.
 *
 * @author Nicholas Rutherford
 */
@Database(
    entities = [CharacterEntity::class, CharacterImageUrlEntity::class, DebugToggleEntity::class],
    autoMigrations = [
        AutoMigration(from = 1, to = 2),
        AutoMigration(from = 2, to = 3),
        AutoMigration(from = 3, to = 4)
    ],
    version = 4,
    exportSchema = true
)
@TypeConverters(DatabaseTypeConverters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun debugToggleDao(): DebugToggleDao

    abstract fun characterDao(): CharacterDao

    abstract fun characterImageDao(): CharacterImageDao

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

