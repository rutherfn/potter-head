@file:Suppress("unused")

package com.nicholas.rutherford.potter.head.di

import com.nicholas.rutherford.potter.head.core.Constants
import com.nicholas.rutherford.potter.head.core.MetroModuleFactory
import com.nicholas.rutherford.potter.head.feature.characters.characterdetail.CharacterDetailViewModelFactory
import com.nicholas.rutherford.potter.head.navigation.di.NavigatorModule
import com.nicholas.rutherford.potter.head.network.di.NetworkModule
import com.nicholas.rutherford.potter.head.saved.state.di.SavedStateModule
import com.nicholas.rutherford.potter.head.feature.characters.characterdetail.CharacterDetailViewModel
import dev.zacsweers.metro.DependencyGraph
import dev.zacsweers.metro.Provides

/**
 * Root dependency graph for the application.
 * Aggregates all feature modules and provides access to their dependencies.
 *
 * Metro will generate AppGraph$$$MetroGraph that provides NetworkModule, SavedStateModule,
 * and NavigatorModule. Each module is provided by creating its respective Metro-generated graph instance.
 *
 * @author Nicholas Rutherford
 */
@DependencyGraph
interface AppGraph {
    val networkModule: NetworkModule
    val navigatorModule: NavigatorModule
    val characterDetailViewModelFactory: CharacterDetailViewModelFactory

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
                moduleName = Constants.NETWORK_MODULE_NAME
            )

        /**
         * Provides the SavedStateModule instance using Metro's generated graph.
         * Metro generates SavedStateModule$$$MetroGraph which implements SavedStateModule.
         *
         * This method delegates to [MetroModuleFactory] to handle the actual creation logic,
         * keeping the AppGraph interface clean and focused on dependency provision.
         *
         * @return A [SavedStateModule] instance created from the Metro-generated graph.
         * @throws IllegalStateException if the SavedStateModule instance cannot be created.
         */
        @Provides
        fun provideSavedStateModule(): SavedStateModule =
            MetroModuleFactory.create(
                interfaceClassName = Constants.SAVED_STATE_MODULE_CLASS_NAME,
                implementationClassName = Constants.SAVED_STATE_MODULE_METRO_GRAPH_CLASS_NAME,
                moduleName = Constants.SAVED_STATE_MODULE_NAME
            )

        /**
         * Provides the NavigatorModule instance using Metro's generated graph.
         * Metro generates NavigatorModule$$$MetroGraph which implements NavigatorModule.
         *
         * This method delegates to [MetroModuleFactory] to handle the actual creation logic,
         * keeping the AppGraph interface clean and focused on dependency provision.
         *
         * @return A [NavigatorModule] instance created from the Metro-generated graph.
         * @throws IllegalStateException if the NavigatorModule instance cannot be created.
         */
        @Provides
        fun provideNavigatorModule(): NavigatorModule =
            MetroModuleFactory.create(
                interfaceClassName = Constants.NAVIGATOR_MODULE_CLASS_NAME,
                implementationClassName = Constants.NAVIGATOR_MODULE_METRO_GRAPH_CLASS_NAME,
                moduleName = Constants.NAVIGATOR_MODULE_NAME
            )

        /**
         * Provides a [CharacterDetailViewModelFactory] instance.
         *
         * This factory is used to create [CharacterDetailViewModel] instances with SavedStateHandle
         * as an assisted parameter. The repository and navigator are injected via Metro.
         *
         * @param networkModule The [NetworkModule] providing the repository.
         * @param navigatorModule The [NavigatorModule] providing the navigator.
         * @return A [CharacterDetailViewModelFactory] instance with dependencies injected.
         */
        @Provides
        fun provideCharacterDetailViewModelFactory(
            networkModule: NetworkModule,
            navigatorModule: NavigatorModule
        ): CharacterDetailViewModelFactory =
            CharacterDetailViewModelFactory(
                repository = networkModule.harryPotterApiRepository,
                navigator = navigatorModule.navigator
            )
    }
}
