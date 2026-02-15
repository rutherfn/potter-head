package com.nicholas.rutherford.potter.head.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.nicholas.rutherford.potter.head.database.dao.CharacterDao
import com.nicholas.rutherford.potter.head.database.dao.DebugToggleDao
import com.nicholas.rutherford.potter.head.database.entity.CharacterEntity
import com.nicholas.rutherford.potter.head.database.entity.DebugToggleEntity
import com.nicholas.rutherford.potter.head.database.entity.WandEntity
import com.nicholas.rutherford.potter.head.database.typeconverter.DatabaseTypeConverters

/**
 * Room database for the application.
 * Handles database creation, migrations, and provides access to DAOs.
 *
 * @author Nicholas Rutherford
 */
@Database(
    entities = [CharacterEntity::class, DebugToggleEntity::class, WandEntity::class],
    version = 1,
    exportSchema = true
)
@TypeConverters(DatabaseTypeConverters::class)
abstract class AppDatabase : RoomDatabase() {

    /**
     * Provides access to the DebugToggleDao.
     */
    abstract fun debugToggleDao(): DebugToggleDao

    /**
     * Provides access to the CharacterDao.
     */
    abstract fun characterDao(): CharacterDao


    companion object {
        private const val DATABASE_NAME = "potter_head_database"

        /**
         * Creates an instance of AppDatabase.
         * Uses Room's database builder.
         *
         * @param context The application context.
         * @return An instance of AppDatabase.
         */
        fun create(context: Context): AppDatabase {
            return Room.databaseBuilder(
                context,
                AppDatabase::class.java,
                DATABASE_NAME
            ).build()
        }
    }
}

