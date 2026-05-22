@file:Suppress("unused")

package com.nicholas.rutherford.potter.head

import android.app.Application
import android.content.Context
import co.touchlab.kermit.Logger
import com.nicholas.rutherford.potter.head.base.view.model.NavigatorProvider
import com.nicholas.rutherford.potter.head.base.view.model.ViewModelFactoryProvider
import com.nicholas.rutherford.potter.head.di.AppGraph
import com.nicholas.rutherford.potter.head.di.AppGraphImpl
import com.nicholas.rutherford.potter.head.di.ViewModelFactory
import com.nicholas.rutherford.potter.head.entry.point.applyThemePreferenceNightMode
import com.nicholas.rutherford.potter.head.entry.point.di.AppBarFactoryProvider
import com.nicholas.rutherford.potter.head.entry.point.navigation.appbar.AppBarFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import com.nicholas.rutherford.potter.head.feature.data.store.DataStorePreferenceReaderProvider
import com.nicholas.rutherford.potter.head.feature.data.store.reader.DataStorePreferenceReader
import com.nicholas.rutherford.potter.head.navigation.Navigator
import androidx.lifecycle.ViewModelProvider as LifeCycleViewModelProvider

/**
 * Custom [Application] class for the Potter Head app.
 *
 * This class serves as the entry point for the application and provides:
 * - Access to the dependency injection graph ([AppGraph])
 * - ViewModel factory for creating ViewModels with dependency injection
 * - Utility method to retrieve the application instance from any context
 *
 * The application initializes the [AppGraph] which provides access to all
 * dependency modules (network, navigation, etc.) throughout the app lifecycle.
 *
 * @author Nicholas Rutherford
 */
class PotterHeadApplication :
    Application(),
    ViewModelFactoryProvider,
    NavigatorProvider,
    AppBarFactoryProvider,
    DataStorePreferenceReaderProvider {

    /**
     * Kermit Logger for this class.
     */
    private val log = Logger.withTag(tag = "PotterHeadApplication")

    val appGraph: AppGraph by lazy { AppGraphImpl(context = this) }

    val viewModelFactory: ViewModelFactory by lazy { ViewModelFactory(appGraph = appGraph, application = this) }

    override fun onCreate() {
        super.onCreate()
        runBlocking(context = Dispatchers.IO) {
            appGraph.dataStoreModule.dataStorePreferenceReader.ensureThemePreferenceLoaded()
        }
        applyThemePreferenceNightMode(
            themePreferenceValue = appGraph.dataStoreModule.dataStorePreferenceReader.peekThemePreferenceValue()
        )
    }

    override fun getViewModelFactory(): LifeCycleViewModelProvider.Factory = viewModelFactory

    override fun getNavigator(): Navigator = appGraph.navigatorModule.navigator

    override fun getAppBarFactory(): AppBarFactory = appGraph.appBarModule.appBarFactory

    override fun getDataStorePreferenceReader(): DataStorePreferenceReader = appGraph.dataStoreModule.dataStorePreferenceReader

    companion object {
        @JvmStatic
        fun from(context: Context): PotterHeadApplication = context.applicationContext as PotterHeadApplication
    }
}
