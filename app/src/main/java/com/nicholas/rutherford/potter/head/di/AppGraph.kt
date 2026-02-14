@file:Suppress("unused")

package com.nicholas.rutherford.potter.head.di

import com.nicholas.rutherford.potter.head.core.Constants
import com.nicholas.rutherford.potter.head.feature.characters.characterdetail.CharacterDetailViewModelFactory
import com.nicholas.rutherford.potter.head.navigation.di.NavigatorModule
import com.nicholas.rutherford.potter.head.network.di.NetworkModule
import com.nicholas.rutherford.potter.head.saved.state.di.SavedStateModule
import com.nicholas.rutherford.potter.head.navigation.Navigator
import com.nicholas.rutherford.potter.head.navigation.NavigatorImpl
import com.nicholas.rutherford.potter.head.saved.state.SavedStateHandleFactory
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.Strictness
import com.nicholas.rutherford.potter.head.network.HarryPotterApiRepository
import com.nicholas.rutherford.potter.head.network.HarryPotterApiRepositoryImpl
import com.nicholas.rutherford.potter.head.network.HarryPotterApiService
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
    val characterDetailViewModelFactory: CharacterDetailViewModelFactory
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
 * Implementation of NetworkModule that provides network-related dependencies.
 */
private class NetworkModuleImpl : NetworkModule {
    override val gson: Gson by lazy {
        GsonBuilder()
            .setStrictness(Strictness.LENIENT)
            .create()
    }

    override val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(Constants.BASE_API_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    override val harryPotterApiService: HarryPotterApiService by lazy {
        retrofit.create(HarryPotterApiService::class.java)
    }

    override val harryPotterApiRepository: HarryPotterApiRepository by lazy {
        HarryPotterApiRepositoryImpl(apiService = harryPotterApiService)
    }
}

/**
 * Implementation of AppGraph that creates and provides all dependency modules.
 */
internal class AppGraphImpl : AppGraph {
    override val networkModule: NetworkModule by lazy {
        NetworkModuleImpl()
    }

    override val navigatorModule: NavigatorModule by lazy {
        NavigatorModuleImpl()
    }

    override val characterDetailViewModelFactory: CharacterDetailViewModelFactory by lazy {
        CharacterDetailViewModelFactory(
            repository = networkModule.harryPotterApiRepository,
            navigator = navigatorModule.navigator
        )
    }
}
