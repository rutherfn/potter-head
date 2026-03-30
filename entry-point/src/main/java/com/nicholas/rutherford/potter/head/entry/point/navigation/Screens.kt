package com.nicholas.rutherford.potter.head.entry.point.navigation

import com.nicholas.rutherford.potter.head.core.Constants

/**
 * Sealed class representing all navigation screens in the application.
 * All routes and titles are defined in [Constants].
 *
 * @param route The route string for the screen.
 * @param title The title string for the screen.
 * @param showBottomNavigation Whether the bottom navigation bar should be displayed on this screen.
 *                             Defaults to true. Set to false for modal-like screens (e.g., filters, detail screens).
 *
 * @author Nicholas Rutherford
 */
sealed class Screens(
    val route: String,
    val title: String,
    val showBottomNavigation: Boolean = true
) {

    object Characters : Screens(
        route = Constants.NavigationDestinations.CHARACTERS_SCREEN,
        title = Constants.ScreenTitles.CHARACTERS,
        showBottomNavigation = true
    )

    object Spells : Screens(
        route = Constants.NavigationDestinations.SPELLS_SCREEN,
        title = Constants.ScreenTitles.SPELLS,
        showBottomNavigation = true
    )

    object CharactersDetail : Screens(
        route = Constants.NavigationDestinations.CHARACTER_DETAIL_SCREEN_WITH_PARAMS,
        title = Constants.ScreenTitles.CHARACTER_DETAIL,
        showBottomNavigation = false
    )

    object CharacterFilters : Screens(
        route = Constants.NavigationDestinations.CHARACTERS_FILTERS_SCREEN,
        title = Constants.ScreenTitles.CHARACTERS_FILTERS,
        showBottomNavigation = false
    )

    object Quizzes : Screens(
        route = Constants.NavigationDestinations.QUIZZES_SCREEN,
        title = Constants.ScreenTitles.QUIZZES,
        showBottomNavigation = true
    )

    object TakeQuiz : Screens(
        route = Constants.NavigationDestinations.TAKE_QUIZ_SCREEN_WITH_PARAMS,
        title = Constants.ScreenTitles.TAKE_QUIZ
    )

    object QuizResult : Screens(
        route = Constants.NavigationDestinations.QUIZ_RESULT_SCREEN_WITH_PARAMS,
        title = Constants.ScreenTitles.QUIZ_RESULT
    )

    object QuizDetail : Screens(
        route = Constants.NavigationDestinations.QUIZ_DETAIL_SCREEN_WITH_PARAMS,
        title = Constants.ScreenTitles.QUIZ_DETAIL,
        showBottomNavigation = false
    )

    object Settings : Screens(
        route = Constants.NavigationDestinations.SETTINGS_SCREEN,
        title = Constants.ScreenTitles.SETTINGS,
        showBottomNavigation = true
    )

    /**
     * Helper function to find a screen by its route and check if it should show bottom navigation.
     *
     * This function handles both exact route matches and parameterized routes (e.g., routes with query params or path params).
     *
     * @param route The route string to match. Can be null, an exact route, or a parameterized route.
     * @return true if the screen should show bottom navigation, false otherwise.
     *         Returns false by default if the route doesn't match any known screen.
     */
    companion object {
        fun shouldShowBottomNavigation(route: String?): Boolean {
            if (route == null) {
                return false
            }

            return when {
                route.startsWith(Characters.route) -> Characters.showBottomNavigation
                route.startsWith(CharacterFilters.route) -> CharacterFilters.showBottomNavigation
                route.startsWith(Quizzes.route) -> Quizzes.showBottomNavigation
                route.startsWith(Settings.route) -> Settings.showBottomNavigation
                route.startsWith(CharactersDetail.route) -> CharactersDetail.showBottomNavigation
                route.startsWith(QuizDetail.route) -> QuizDetail.showBottomNavigation
                route.startsWith(Spells.route) -> Spells.showBottomNavigation
                else -> false
            }
        }
    }
}