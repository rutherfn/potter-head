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
import com.nicholas.rutherford.potter.head.entry.point.di.AppBarFactoryProvider
import com.nicholas.rutherford.potter.head.entry.point.navigation.appbar.AppBarFactory
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
    AppBarFactoryProvider {
    /**
     * Kermit Logger for this class.
     */
    private val log = Logger.withTag(tag = "PotterHeadApplication")

    val appGraph: AppGraph by lazy { AppGraphImpl(context = this) }

    val viewModelFactory: ViewModelFactory by lazy { ViewModelFactory(appGraph = appGraph) }

    override fun getViewModelFactory(): LifeCycleViewModelProvider.Factory = viewModelFactory

    override fun getNavigator(): Navigator = appGraph.navigatorModule.navigator

    override fun getAppBarFactory(): AppBarFactory = appGraph.appBarModule.appBarFactory

    companion object {
        @JvmStatic
        fun from(context: Context): PotterHeadApplication = context.applicationContext as PotterHeadApplication
    }
}
