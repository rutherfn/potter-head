package com.nicholas.rutherford.potter.head.navigation

import androidx.navigation.NavOptions
import com.nicholas.rutherford.potter.head.core.Constants

/**
 * Object providing predefined [NavigationAction] implementations for common navigation scenarios.
 *
 * This object contains nested objects that group related navigation actions by feature or domain.
 * Each nested object provides factory methods to create [NavigationAction] instances with
 * the appropriate destination routes and navigation options.
 *
 * Using predefined navigation actions ensures:
 * - Consistent route formatting across the application
 * - Type-safe navigation with compile-time route validation
 * - Centralized navigation action definitions
 *
 * @see NavigationAction for the interface definition
 * @see SimpleNavigationAction for simple navigation actions
 *
 * @author Nicholas Rutherford
 */
object DefinedNavigationActions {

    object Characters {
        fun characterDetails(id: String) = object : NavigationAction {
            override val destination: String = "${Constants.NavigationDestinations.CHARACTER_DETAIL_SCREEN}?id=$id"
            override val navOptions = NavOptions.Builder().build()
            }
        }
    }