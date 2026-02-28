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
    val characterDetail = listOf(
        navArgument(Constants.NamedArguments.ID) { type = NavType.StringType }
    )
}