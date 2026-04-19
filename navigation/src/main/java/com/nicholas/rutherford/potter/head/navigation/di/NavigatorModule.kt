package com.nicholas.rutherford.potter.head.navigation.di

import com.nicholas.rutherford.potter.head.navigation.Navigator

/**
 * Dependency graph interface for navigation-related components.
 * Provides access to [Navigator] for handling navigation actions throughout the app.
 *
 * The Navigator uses StateFlow to emit navigation actions that can be observed by the
 * MainActivity or other navigation handlers to perform actual navigation.
 *
 * @author Nicholas Rutherford
 */
interface NavigatorModule {
    /**
     * Provides access to [Navigator] for handling navigation actions.
     * ViewModels can inject this to trigger navigation without directly accessing NavController.
     */
    val navigator: Navigator
}
