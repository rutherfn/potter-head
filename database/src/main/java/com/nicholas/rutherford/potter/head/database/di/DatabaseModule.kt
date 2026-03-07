package com.nicholas.rutherford.potter.head.database.di

import com.nicholas.rutherford.potter.head.database.AppDatabase
import com.nicholas.rutherford.potter.head.database.dao.CharacterDao
import com.nicholas.rutherford.potter.head.database.dao.CharacterFilterDao
import com.nicholas.rutherford.potter.head.database.dao.CharacterImageDao
import com.nicholas.rutherford.potter.head.database.dao.DebugToggleDao
import com.nicholas.rutherford.potter.head.database.repository.CharacterFilterRepository
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
    val appDatabase: AppDatabase
    val characterDao: CharacterDao
    val characterImageDao: CharacterImageDao
    val debugToggleDao: DebugToggleDao
    val characterFilterDao: CharacterFilterDao
    val characterFilterRepository: CharacterFilterRepository
    val characterRepository: CharacterRepository
    val characterImageRepository: CharacterImageRepository
    val debugToggleRepository: DebugToggleRepository
}

