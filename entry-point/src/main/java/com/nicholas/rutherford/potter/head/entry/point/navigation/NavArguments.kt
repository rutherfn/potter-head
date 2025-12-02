package com.nicholas.rutherford.potter.head.entry.point.navigation

import androidx.navigation.NavType
import androidx.navigation.navArgument
import androidx.navigation.NavArgument
import com.nicholas.rutherford.potter.head.core.Constants

/**
 * Object containing navigation argument definitions for Jetpack Compose Navigation.
 *
 * This object provides pre-configured navigation arguments that are used when defining
 * composable routes in the navigation graph. Each property represents a list of
 * [NavArgument] objects that define the parameters required for
 * a specific screen.
 *
 * All argument names are defined in [Constants.NamedArguments] to ensure consistency
 * across the application.
 *
 * @author Nicholas Rutherford
 */
object NavArguments {

    /**
     * Navigation arguments for the character detail screen.
     *
     * Defines a single string argument for the character ID parameter.
     * This is used when navigating to [Screens.CharactersDetail] screen.
     *
     * @return A list containing a single [androidx.navigation.NavArgument] with:
     * - Name: [Constants.NamedArguments.ID]
     * - Type: [NavType.StringType]
     */
    val characterDetail = listOf(
        navArgument(Constants.NamedArguments.ID) { type = NavType.StringType }
    )
}