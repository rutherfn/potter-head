package com.nicholas.rutherford.potter.head.entry.point.navigation

import com.nicholas.rutherford.potter.head.core.Constants

/**
 * Sealed class representing all navigation screens in the application.
 * All routes and titles are defined in [Constants].
 *
 * @author Nicholas Rutherford
 */
sealed class Screens(val route: String, val title: String) {

    object Characters : Screens(
        route = Constants.NavigationDestinations.CHARACTERS_SCREEN,
        title = Constants.ScreenTitles.CHARACTERS
    )

    object CharactersDetail : Screens(
        route = Constants.NavigationDestinations.CHARACTER_DETAIL_SCREEN_WITH_PARAMS,
        title = Constants.ScreenTitles.CHARACTER_DETAIL
    )

    object Quizzes : Screens(
        route = Constants.NavigationDestinations.QUIZZES_SCREEN,
        title = Constants.ScreenTitles.QUIZZES
    )

    object Settings : Screens(
        route = Constants.NavigationDestinations.SETTINGS_SCREEN,
        title = Constants.ScreenTitles.SETTINGS
    )
}