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

    /**
     * Navigation actions related to the characters feature.
     *
     * This object provides factory methods for navigating to character-related screens,
     * such as character detail screens with proper parameter formatting.
     */
    object Characters {
        /**
         * Creates a navigation action to navigate to the character detail screen.
         *
         * This method constructs a navigation action that navigates to the character detail
         * screen with the provided character ID as a query parameter.
         *
         * @param id The ID of the character to display details for.
         * @return A [NavigationAction] that navigates to the character detail screen with the given ID.
         */
        fun characterDetails(id: String) = object : NavigationAction {
            override val destination: String = "${Constants.NavigationDestinations.CHARACTER_DETAIL_SCREEN}?id=$id"
            override val navOptions = NavOptions.Builder().build()
            }
        }
    }