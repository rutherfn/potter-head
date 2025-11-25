package com.nicholas.rutherford.potter.head.entry.point.navigation

import com.nicholas.rutherford.potter.head.core.Constants

/**
 * Sealed class representing all navigation screens in the application.
 *
 * Each screen object contains:
 * - [route]: The navigation route string used for Jetpack Compose Navigation
 * - [title]: The display title for the screen
 *
 * All routes and titles are defined in [Constants] to ensure consistency
 * across the application.
 *
 * @property route The navigation route for this screen
 * @property title The display title for this screen
 *
 * @author Nicholas Rutherford
 */
sealed class Screens(val route: String, val title: String) {
    /**
     * Screen for displaying the list of characters.
     */
    object Characters : Screens(
        route = Constants.NavigationDestinations.CHARACTERS_SCREEN,
        title = Constants.ScreenTitles.CHARACTERS
    )

    /**
     * Screen for displaying detailed information about a specific character.
     * The route includes a parameter for the character ID.
     */
    object CharactersDetail : Screens(
        route = Constants.NavigationDestinations.CHARACTER_DETAIL_SCREEN_WITH_PARAMS,
        title = Constants.ScreenTitles.CHARACTER_DETAIL
    )

    /**
     * Screen for displaying available quizzes.
     */
    object Quizzes : Screens(
        route = Constants.NavigationDestinations.QUIZZES_SCREEN,
        title = Constants.ScreenTitles.QUIZZES
    )

    /**
     * Screen for displaying application settings.
     */
    object Settings : Screens(
        route = Constants.NavigationDestinations.SETTINGS_SCREEN,
        title = Constants.ScreenTitles.SETTINGS
    )
}