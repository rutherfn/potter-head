package com.nicholas.rutherford.potter.head.database.di

import android.content.Context
import com.nicholas.rutherford.potter.head.database.AppDatabase
import com.nicholas.rutherford.potter.head.database.dao.DebugToggleDao
import com.nicholas.rutherford.potter.head.database.repository.DebugToggleRepository
import com.nicholas.rutherford.potter.head.database.repository.DebugToggleRepositoryImpl

/**
 * Dependency graph interface for database-related components.
 * Provides access to Room database, DAOs, and repositories.
 *
 * @author Nicholas Rutherford
 */
interface DatabaseModule {
    /**
     * The Room database instance.
     */
    val appDatabase: AppDatabase

    /**
     * DAO for accessing debug toggles.
     */
    val debugToggleDao: DebugToggleDao

    /**
     * Repository for managing debug toggles.
     */
    val debugToggleRepository: DebugToggleRepository
}

