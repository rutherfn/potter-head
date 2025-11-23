package com.nicholas.rutherford.potter.head

import android.app.Application
import co.touchlab.kermit.Logger
import com.nicholas.rutherford.potter.head.base.view.model.ViewModelFactoryProvider
import com.nicholas.rutherford.potter.head.core.Constants
import com.nicholas.rutherford.potter.head.di.AppGraph
import androidx.lifecycle.ViewModelProvider as LifeCycleViewModelProvider
import android.content.Context
import com.nicholas.rutherford.potter.head.di.ViewModelFactory

/**
 * Custom [Application] class for the Potter Head app.
 *
 * This class serves as the entry point for the application and provides:
 * - Access to the Metro dependency injection graph ([AppGraph])
 * - ViewModel factory for creating ViewModels with dependency injection
 * - Utility method to retrieve the application instance from any context
 *
 * The application initializes the Metro-generated [AppGraph] which provides access to all
 * dependency modules (network, database, etc.) throughout the app lifecycle.
 *
 * @author Nicholas Rutherford
 */
class PotterHeadApplication :
    Application(),
    ViewModelFactoryProvider {
    /**
     * Kermit Logger for this class.
     */
    private val log = Logger.withTag(tag = "PotterHeadApplication")

    /**
     * Metro generates AppGraph$$$MetroGraph as the implementation.
     * This provides access to all dependency graphs in the application.
     */
    val appGraph: AppGraph by lazy {
        try {
            Class
                .forName(Constants.APP_GRAPH_METRO_GRAPH_CLASS_NAME)
                .getDeclaredConstructor()
                .newInstance() as AppGraph
        } catch (e: Exception) {
            log.e("Failed to create AppGraph instance with exception message --- ${e.message}")
            throw IllegalStateException(
                "Failed to create AppGraph instance. Make sure Metro has generated the graph.",
                e
            )
        }
    }

    /**
     * ViewModelFactory instance using AppGraph.
     * This is the single source of truth for ViewModel creation.
     */
    val viewModelFactory: ViewModelFactory by lazy {
        ViewModelFactory(appGraph)
    }

    /**
     * Returns the [LifeCycleViewModelProvider.Factory] instance for creating ViewModels with dependency injection.
     *
     * This factory uses the [AppGraph] to provide dependencies to ViewModels via constructor injection.
     * All ViewModels created through this factory will have their dependencies automatically injected.
     *
     * @return A [LifeCycleViewModelProvider.Factory] instance configured with the application's dependency graph.
     */
    override fun getViewModelFactory(): LifeCycleViewModelProvider.Factory = viewModelFactory

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
