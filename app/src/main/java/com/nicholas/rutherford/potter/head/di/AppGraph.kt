package com.nicholas.rutherford.potter.head.di

import com.nicholas.rutherford.potter.head.core.Constants
import com.nicholas.rutherford.potter.head.core.MetroModuleFactory
import com.nicholas.rutherford.potter.head.network.di.NetworkModule
import dev.zacsweers.metro.DependencyGraph
import dev.zacsweers.metro.Provides

/**
 * Root dependency graph for the application.
 * Aggregates all feature modules and provides access to their dependencies.
 *
 * Metro will generate AppGraph$$$MetroGraph that provides NetworkModule.
 * NetworkModule is provided by creating NetworkModule$$$MetroGraph instance.
 *
 * @author Nicholas Rutherford
 */
@DependencyGraph
interface AppGraph {
    val networkModule: NetworkModule

    companion object {
        /**
         * Provides the NetworkModule instance using Metro's generated graph.
         * Metro generates NetworkModule$$$MetroGraph which implements NetworkModule.
         *
         * This method delegates to [MetroModuleFactory] to handle the actual creation logic,
         * keeping the AppGraph interface clean and focused on dependency provision.
         *
         * @return A [NetworkModule] instance created from the Metro-generated graph.
         * @throws IllegalStateException if the NetworkModule instance cannot be created.
         */
        @Provides
        fun provideNetworkModule(): NetworkModule =
            MetroModuleFactory.create(
                interfaceClassName = Constants.NETWORK_MODULE_CLASS_NAME,
                implementationClassName = Constants.NETWORK_MODULE_METRO_GRAPH_CLASS_NAME,
                moduleName = "NetworkModule"
            )
    }
}
