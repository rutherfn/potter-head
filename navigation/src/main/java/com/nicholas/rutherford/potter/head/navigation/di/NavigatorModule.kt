package com.nicholas.rutherford.potter.head.navigation.di

import com.nicholas.rutherford.potter.head.navigation.Navigator
import com.nicholas.rutherford.potter.head.navigation.NavigatorImpl
import dev.zacsweers.metro.DependencyGraph
import dev.zacsweers.metro.Provides

/**
 * Metro dependency graph for navigation-related components.
 * Provides access to [Navigator] for handling navigation actions throughout the app.
 *
 * The Navigator uses StateFlow to emit navigation actions that can be observed by the
 * MainActivity or other navigation handlers to perform actual navigation.
 *
 * @author Nicholas Rutherford
 */
@DependencyGraph
interface NavigatorModule {

    /**
     * Provides access to [Navigator] for handling navigation actions.
     * ViewModels can inject this to trigger navigation without directly accessing NavController.
     */
    val navigator: Navigator

    companion object {
        /**
         * Singleton Navigator instance shared across the entire app.
         * This ensures that all ViewModels and MainActivity observe the same StateFlow.
         */
        private val navigatorInstance: Navigator by lazy { NavigatorImpl() }

        /**
         * Provides an instance of [Navigator].
         *
         * Returns a singleton instance to ensure all components (ViewModels, MainActivity, etc.)
         * use the same Navigator instance and observe the same StateFlows.
         *
         * @return The singleton [Navigator] instance.
         */
        @Provides
        fun provideNavigator(): Navigator = navigatorInstance
    }
}