package com.nicholas.rutherford.potter.head.database.di

import com.nicholas.rutherford.potter.head.database.AppDatabase
import com.nicholas.rutherford.potter.head.database.dao.CharacterDao
import com.nicholas.rutherford.potter.head.database.dao.CharacterImageDao
import com.nicholas.rutherford.potter.head.database.dao.DebugToggleDao
import com.nicholas.rutherford.potter.head.database.repository.CharacterImageRepository
import com.nicholas.rutherford.potter.head.database.repository.CharacterRepository
import com.nicholas.rutherford.potter.head.database.repository.DebugToggleRepository

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
     * DAO for accessing characters.
     */
    val characterDao: CharacterDao

    /**
     * DAO for accessing characters urls.
     */
    val characterImageDao: CharacterImageDao

    /**
     * DAO for accessing debug toggles.
     */
    val debugToggleDao: DebugToggleDao

    /**
     * Repository for managing characters.
     */
    val characterRepository: CharacterRepository

    /**
     * Repository for managing character urls.
     */
    val characterImageRepository: CharacterImageRepository

    /**
     * Repository for managing debug toggles.
     */
    val debugToggleRepository: DebugToggleRepository
}

