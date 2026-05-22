package com.nicholas.rutherford.potter.head.database

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

/**
 * Manual Room migrations for schema changes that are not safe to auto-migrate.
 */
object DatabaseMigrations {

    /**
     * Recreates saved quizzes so `id` uses SQLite AUTOINCREMENT instead of a manual primary key.
     * Auto-migration for this change has been unreliable on some devices.
     * Todo - Look into if we really need this still for the future
     */
    val MIGRATION_9_10: Migration = object : Migration(startVersion = 9, endVersion = 10) {
        override fun migrate(db: SupportSQLiteDatabase) {
            db.execSQL(
                """
                CREATE TABLE IF NOT EXISTS `savedQuizzes_new` (
                    `id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                    `quizId` TEXT NOT NULL,
                    `quizTitle` TEXT NOT NULL,
                    `quizDescription` TEXT NOT NULL,
                    `quizImageUrl` TEXT NOT NULL,
                    `resultText` TEXT NOT NULL,
                    `resultImageUrl` TEXT NOT NULL,
                    `resultMoreInfo` TEXT NOT NULL,
                    `savedAt` INTEGER NOT NULL,
                    `questions` TEXT NOT NULL
                )
                """.trimIndent()
            )
            db.execSQL(
                """
                INSERT INTO `savedQuizzes_new` (
                    `id`,
                    `quizId`,
                    `quizTitle`,
                    `quizDescription`,
                    `quizImageUrl`,
                    `resultText`,
                    `resultImageUrl`,
                    `resultMoreInfo`,
                    `savedAt`,
                    `questions`
                )
                SELECT
                    `id`,
                    `quizId`,
                    `quizTitle`,
                    `quizDescription`,
                    `quizImageUrl`,
                    `resultText`,
                    `resultImageUrl`,
                    `resultMoreInfo`,
                    `savedAt`,
                    `questions`
                FROM `savedQuizzes`
                """.trimIndent()
            )
            db.execSQL("DROP TABLE `savedQuizzes`")
            db.execSQL("ALTER TABLE `savedQuizzes_new` RENAME TO `savedQuizzes`")
        }
    }
}
