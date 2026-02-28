@file:Suppress("unused")

package com.nicholas.rutherford.potter.head.di

import android.content.Context
import co.touchlab.kermit.Logger
import com.nicholas.rutherford.potter.head.core.Constants
import com.nicholas.rutherford.potter.head.database.AppDatabase
import com.nicholas.rutherford.potter.head.database.DebugToggleKeys
import com.nicholas.rutherford.potter.head.database.di.DatabaseModule
import com.nicholas.rutherford.potter.head.feature.characters.characterdetail.CharacterDetailViewModelFactory
import com.nicholas.rutherford.potter.head.navigation.di.NavigatorModule
import com.nicholas.rutherford.potter.head.network.di.NetworkModule
import com.nicholas.rutherford.potter.head.saved.state.di.SavedStateModule
import com.nicholas.rutherford.potter.head.scope.di.ScopeModule
import com.nicholas.rutherford.potter.head.navigation.Navigator
import com.nicholas.rutherford.potter.head.navigation.NavigatorImpl
import com.nicholas.rutherford.potter.head.saved.state.SavedStateHandleFactory
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.Strictness
import com.nicholas.rutherford.potter.head.database.dao.CharacterDao
import com.nicholas.rutherford.potter.head.database.repository.CharacterRepository
import com.nicholas.rutherford.potter.head.database.repository.CharacterRepositoryImpl
import com.nicholas.rutherford.potter.head.database.repository.DebugToggleRepository
import com.nicholas.rutherford.potter.head.database.repository.DebugToggleRepositoryImpl
import android.net.ConnectivityManager
import com.nicholas.rutherford.potter.head.database.dao.CharacterImageDao
import com.nicholas.rutherford.potter.head.database.repository.CharacterImageRepository
import com.nicholas.rutherford.potter.head.database.repository.CharacterImageRepositoryImpl
import com.nicholas.rutherford.potter.head.entry.point.di.AppBarFactoryModule
import com.nicholas.rutherford.potter.head.entry.point.navigation.appbar.AppBarFactory
import com.nicholas.rutherford.potter.head.entry.point.navigation.appbar.AppBarFactoryImpl
import com.nicholas.rutherford.potter.head.network.HarryPotterApiRepository
import com.nicholas.rutherford.potter.head.network.HarryPotterApiRepositoryImpl
import com.nicholas.rutherford.potter.head.network.HarryPotterApiService
import com.nicholas.rutherford.potter.head.network.NetworkMonitor
import com.nicholas.rutherford.potter.head.network.NetworkMonitorImpl
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Root dependency graph for the application.
 * Aggregates all feature modules and provides access to their dependencies.
 *
 * @author Nicholas Rutherford
 */
interface AppGraph {
    val networkModule: NetworkModule
    val navigatorModule: NavigatorModule
    val appBarModule: AppBarFactoryModule
    val databaseModule: DatabaseModule
    val scopeModule: ScopeModule
    val characterDetailViewModelFactory: CharacterDetailViewModelFactory
}

/**
 * Implementation of AppBarFactoryModule that provides AppBar factory instances.
 */
private class AppBarFactoryModuleImpl : AppBarFactoryModule {
    private val appBarFactoryInstance: AppBarFactory by lazy { AppBarFactoryImpl() }
    override val appBarFactory: AppBarFactory = appBarFactoryInstance
}

/**
 * Implementation of NavigatorModule that provides navigation-related dependencies.
 */
private class NavigatorModuleImpl : NavigatorModule {
    private val navigatorInstance: Navigator by lazy { NavigatorImpl() }
    override val navigator: Navigator = navigatorInstance
}

/**
 * Implementation of SavedStateModule that provides saved state-related dependencies.
 */
private class SavedStateModuleImpl : SavedStateModule {
    override val savedStateHandleFactory: SavedStateHandleFactory = SavedStateHandleFactory
}

/**
 * Implementation of ScopeModule that provides coroutine scope-related dependencies.
 */
private class ScopeModuleImpl : ScopeModule {
    override val viewModelScope: CoroutineScope by lazy { CoroutineScope(SupervisorJob() + Dispatchers.Default) }
    override val ioScope: CoroutineScope by lazy { CoroutineScope(SupervisorJob() + Dispatchers.IO) }
    override val mainScope: CoroutineScope by lazy { CoroutineScope(SupervisorJob() + Dispatchers.Main) }
    override val defaultScope: CoroutineScope by lazy { CoroutineScope(SupervisorJob() + Dispatchers.Default) }
}

/**
 * Implementation of NetworkModule that provides network-related dependencies.
 */
private class NetworkModuleImpl(private val context: Context) : NetworkModule {
    private val connectivityManager: ConnectivityManager by lazy { context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager }

    override val gson: Gson by lazy {
        GsonBuilder()
            .setStrictness(Strictness.LENIENT)
            .create()
    }

    override val retrofit: Retrofit by lazy {
        Retrofit
            .Builder()
            .baseUrl(Constants.BASE_API_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    override val harryPotterApiService: HarryPotterApiService by lazy { retrofit.create(HarryPotterApiService::class.java) }

    override val harryPotterApiRepository: HarryPotterApiRepository by lazy { HarryPotterApiRepositoryImpl(apiService = harryPotterApiService) }

    override val networkMonitor: NetworkMonitor by lazy { NetworkMonitorImpl(context = context, connectivityManager = connectivityManager) }
}

/**
 * Implementation of DatabaseModule that provides database-related dependencies.
 */
private class DatabaseModuleImpl(private val context: Context) : DatabaseModule {

    /**
     * Kermit Logger for DatabaseModule interactions.
     */
    val log = Logger.withTag(tag = "DatabaseModule")

    /**
     * Coroutine scope for database initialization tasks.
     * Uses SupervisorJob to ensure that if one initialization task fails, others can still run.
     */
    private val initializationScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    override val appDatabase: AppDatabase by lazy {
        AppDatabase.create(context).also { database ->
            initializeDefaultData(database)
        }
    }

    override val debugToggleDao = appDatabase.debugToggleDao()

    override val characterDao: CharacterDao = appDatabase.characterDao()

    override val characterImageDao: CharacterImageDao = appDatabase.characterImageDao()

    override val characterRepository: CharacterRepository by lazy { CharacterRepositoryImpl(dao = characterDao, characterImageDao = characterImageDao) }

    override val characterImageRepository: CharacterImageRepository by lazy { CharacterImageRepositoryImpl(dao = characterImageDao, context = context) }

    override val debugToggleRepository: DebugToggleRepository by lazy { DebugToggleRepositoryImpl(dao = debugToggleDao) }

    /**
     * Initializes default data in the database asynchronously.
     * This runs on a background thread (Dispatchers.IO) to avoid blocking the main thread.
     *
     * This method is designed to be extensible - add initialization logic for
     * new tables here as they are added to the database.
     */
    private fun initializeDefaultData(database: AppDatabase) {
        initializationScope.launch {
            try {
                initializeDebugToggles(database)
            } catch (e: Exception) {
                log.e("Failed to initialize default database data: ${e.message}")
            }
        }
    }

    /**
     * Initializes default debug toggles in the database.
     * Only sets defaults if the toggle doesn't already exist.
     */
    private suspend fun initializeDebugToggles(database: AppDatabase) {
        val repository = DebugToggleRepositoryImpl(dao = database.debugToggleDao())

        val existingToggle = repository.getToggleSync(DebugToggleKeys.SHOULD_SIMULATE_NO_INTERNET_CONNECTION)
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
}

/**
 * Implementation of AppGraph that creates and provides all dependency modules.
 */
internal class AppGraphImpl(private val context: Context) : AppGraph {
    override val networkModule: NetworkModule by lazy { NetworkModuleImpl(context = context) }

    override val navigatorModule: NavigatorModule by lazy { NavigatorModuleImpl() }

    override val databaseModule: DatabaseModule by lazy { DatabaseModuleImpl(context = context) }

    override val scopeModule: ScopeModule by lazy { ScopeModuleImpl() }

    override val appBarModule: AppBarFactoryModule by lazy { AppBarFactoryModuleImpl() }

    override val characterDetailViewModelFactory: CharacterDetailViewModelFactory by lazy {
        CharacterDetailViewModelFactory(
            repository = networkModule.harryPotterApiRepository,
            navigator = navigatorModule.navigator
        )
    }
}
