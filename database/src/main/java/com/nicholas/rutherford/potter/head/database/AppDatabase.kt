package com.nicholas.rutherford.potter.head.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import com.nicholas.rutherford.potter.head.database.dao.DebugToggleDao
import com.nicholas.rutherford.potter.head.database.entity.DebugToggleEntity

/**
 * Room database for the application.
 * Handles database creation, migrations, and provides access to DAOs.
 *
 * @author Nicholas Rutherford
 */
@Database(
    entities = [DebugToggleEntity::class],
    version = 1,
    exportSchema = true
)
abstract class AppDatabase : RoomDatabase() {
    /**
     * Provides access to the DebugToggleDao.
     */
    abstract fun debugToggleDao(): DebugToggleDao

    companion object {
        private const val DATABASE_NAME = "potter_head_database"

        /**
         * Creates an instance of AppDatabase.
         * Uses Room's database builder with auto migrations enabled.
         *
         * @param context The application context.
         * @return An instance of AppDatabase.
         */
        fun create(context: Context): AppDatabase {
            return Room.databaseBuilder(
                context,
                AppDatabase::class.java,
                DATABASE_NAME
            )
                .addMigrations(*getMigrations())
                .build()
        }

        /**
         * Returns all database migrations.
         * Currently empty as we're starting with version 1.
         * Add migrations here as the database schema evolves.
         *
         * @return Array of Migration objects.
         */
        private fun getMigrations(): Array<Migration> {
            return arrayOf(
                // Add migrations here as the database schema evolves
                // Example:
                // Migration(1, 2) { database ->
                //     database.execSQL("ALTER TABLE debug_toggles ADD COLUMN new_column TEXT")
                // }
            )
        }
    }
}

