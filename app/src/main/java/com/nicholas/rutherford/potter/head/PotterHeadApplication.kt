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
    NavigatorProvider {
    /**
     * Kermit Logger for this class.
     */
    private val log = Logger.withTag(tag = "PotterHeadApplication")

    /**
     * AppGraph instance that aggregates all dependency modules.
     * Provides access to network, navigation, database, and other feature modules.
     */
    val appGraph: AppGraph by lazy { AppGraphImpl(context = this) }

    /**
     * ViewModelFactory instance using AppGraph.
     * This is the single source of truth for ViewModel creation.
     */
    val viewModelFactory: ViewModelFactory by lazy { ViewModelFactory(appGraph = appGraph) }

    /**
     * Returns the [LifeCycleViewModelProvider.Factory] instance for creating ViewModels with dependency injection.
     *
     * This factory uses the [AppGraph] to provide dependencies to ViewModels via constructor injection.
     * All ViewModels created through this factory will have their dependencies automatically injected.
     *
     * @return A [LifeCycleViewModelProvider.Factory] instance configured with the application's dependency graph.
     */
    override fun getViewModelFactory(): LifeCycleViewModelProvider.Factory = viewModelFactory

    /**
     * Returns the [Navigator] instance for handling navigation actions.
     *
     * This provides access to the Navigator which can be used to trigger navigation
     * from ViewModels. MainActivity observes the Navigator's StateFlows to perform
     * actual navigation.
     *
     * @return The [Navigator] instance from the AppGraph.
     */
    override fun getNavigator(): Navigator = appGraph.navigatorModule.navigator

    companion object {
        /**
         * Retrieves the [PotterHeadApplication] instance from the given [context].
         *
         * This is a convenience method to access the application instance and its properties
         * (such as [appGraph] and [viewModelFactory]) from any Android context.
         *
         * Usage:
         * ```
         * val application = PotterHeadApplication.from(context)
         * val appGraph = application.appGraph
         * ```
         *
         * @param context The Android context to retrieve the application from.
         * @return The [PotterHeadApplication] instance.
         * @throws ClassCastException if the application context is not an instance of [PotterHeadApplication].
         */
        @JvmStatic
        fun from(context: Context): PotterHeadApplication = context.applicationContext as PotterHeadApplication
    }
}
