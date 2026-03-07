package com.nicholas.rutherford.potter.head.di

import android.content.Context
import co.touchlab.kermit.Logger
import com.nicholas.rutherford.potter.head.database.AppDatabase
import com.nicholas.rutherford.potter.head.database.CharacterFilterType
import com.nicholas.rutherford.potter.head.database.DefaultFilters
import com.nicholas.rutherford.potter.head.database.DebugToggleKeys
import com.nicholas.rutherford.potter.head.database.dao.CharacterDao
import com.nicholas.rutherford.potter.head.database.dao.CharacterFilterDao
import com.nicholas.rutherford.potter.head.database.dao.CharacterImageDao
import com.nicholas.rutherford.potter.head.database.di.DatabaseModule
import com.nicholas.rutherford.potter.head.database.repository.CharacterFilterRepository
import com.nicholas.rutherford.potter.head.database.repository.CharacterFilterRepositoryImpl
import com.nicholas.rutherford.potter.head.database.repository.CharacterImageRepository
import com.nicholas.rutherford.potter.head.database.repository.CharacterImageRepositoryImpl
import com.nicholas.rutherford.potter.head.database.repository.CharacterRepository
import com.nicholas.rutherford.potter.head.database.repository.CharacterRepositoryImpl
import com.nicholas.rutherford.potter.head.database.repository.DebugToggleRepository
import com.nicholas.rutherford.potter.head.database.repository.DebugToggleRepositoryImpl
import com.nicholas.rutherford.potter.head.scope.di.ScopeModule
import kotlinx.coroutines.launch

/**
 * Implementation of [DatabaseModule].
 * Provides database-related dependencies including Room database, DAOs, and repositories.
 * Also handles initialization of default data (debug toggles, character filters).
 *
 * @param context The application context for creating the database.
 * @param scopeModule The scope module providing coroutine scopes for initialization operations.
 *
 * @author Nicholas Rutherford
 */
class DatabaseModuleImpl(
    private val context: Context,
    private val scopeModule: ScopeModule
) : DatabaseModule {
    private val log = Logger.withTag(tag = "DatabaseModule")

    override val appDatabase: AppDatabase by lazy {
        AppDatabase.create(context).also { database ->
            initializeDefaultData(database)
        }
    }

    override val debugToggleDao = appDatabase.debugToggleDao()

    override val characterFilterDao: CharacterFilterDao = appDatabase.characterFilterDao()

    override val characterDao: CharacterDao = appDatabase.characterDao()

    override val characterImageDao: CharacterImageDao = appDatabase.characterImageDao()

    override val characterRepository: CharacterRepository by lazy {
        CharacterRepositoryImpl(dao = characterDao, characterFilterDao = characterFilterDao, characterImageDao = characterImageDao)
    }

    override val characterFilterRepository: CharacterFilterRepository by lazy { CharacterFilterRepositoryImpl(dao = characterFilterDao) }

    override val characterImageRepository: CharacterImageRepository by lazy {
        CharacterImageRepositoryImpl(dao = characterImageDao, context = context)
    }

    override val debugToggleRepository: DebugToggleRepository by lazy { DebugToggleRepositoryImpl(dao = debugToggleDao) }

    private fun initializeDefaultData(database: AppDatabase) {
        scopeModule.ioScope.launch {
            try {
                initializeDebugToggles(database = database)
                initializeCharacterFilters(database = database)
            } catch (e: Exception) {
                log.e("Failed to initialize default database data: ${e.message}")
            }
        }
    }

    private suspend fun initializeDebugToggles(database: AppDatabase) {
        val repository = DebugToggleRepositoryImpl(dao = database.debugToggleDao())
        val existingToggle = repository.getToggleSync(key = DebugToggleKeys.SHOULD_SIMULATE_NO_INTERNET_CONNECTION)

        if (existingToggle == null) {
            log.d("Initializing default debug toggle since existing toggles where null")
            repository.setToggleEnabled(
                key = DebugToggleKeys.SHOULD_SIMULATE_NO_INTERNET_CONNECTION,
                isEnabled = true
            )
        } else {
            log.d("Default debug toggle already exists, skipping initialization")
        }
    }

    private suspend fun initializeCharacterFilters(database: AppDatabase) {
        val repository = CharacterFilterRepositoryImpl(dao = database.characterFilterDao())
        val defaultFiltersByType =
            mapOf(
                CharacterFilterType.HOUSE to DefaultFilters.HouseFilter,
                CharacterFilterType.GENDER to DefaultFilters.genderFilter,
                CharacterFilterType.SPECIES to DefaultFilters.speciesFilter,
                CharacterFilterType.HOGWARTS_AFFILIATION to DefaultFilters.hogwartsAffiliation
            )

        defaultFiltersByType.forEach { (filterType, defaultFilter) ->
            val existingFilters = repository.getCharacterFiltersByTypeSync(filterType = filterType)

            if (existingFilters.isEmpty()) {
                log.d("Initializing default character filter for type: ${filterType.name}")
                repository.insertFilter(filter = defaultFilter)
            } else {
                log.d("Default character filter for type ${filterType.name} already exists, skipping initialization")
            }
        }
    }
}
