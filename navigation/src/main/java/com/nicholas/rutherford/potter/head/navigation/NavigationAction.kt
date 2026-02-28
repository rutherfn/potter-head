package com.nicholas.rutherford.potter.head.navigation

import androidx.navigation.NavOptions

/**
 * Interface representing a navigation action to be performed.
 *
 * This interface defines the contract for navigation actions that can be emitted
 * by the [Navigator] and consumed by the UI layer. Each navigation action contains:
 * - A destination route string
 * - Optional navigation options for customizing the navigation behavior
 *
 * Implementations of this interface can provide custom navigation behavior by
 * overriding the [navOptions] property. The default implementation provides
 * basic [NavOptions] with no special configuration.
 *
 * @property destination The route string to navigate to.
 * @property navOptions The [NavOptions] to use for this navigation action.
 *                      Defaults to basic [NavOptions] with no special configuration.
 *
 * @see SimpleNavigationAction for a simple data class implementation
 * @see DefinedNavigationActions for predefined navigation actions
 *
 * @author Nicholas Rutherford
 */
interface NavigationAction {
    val destination: String

    val navOptions: NavOptions
        get() = NavOptions.Builder().build()
}