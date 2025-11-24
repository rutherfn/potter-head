package com.nicholas.rutherford.potter.head.entry.point.navigation

import com.nicholas.rutherford.potter.head.core.Constants

sealed class Screens(val route: String, val title: String) {
    object Characters : Screens("characters", "Characters")
    object CharactersDetail: Screens(route = Constants.NavigationDestinations.CHARACTER_DETAIL_SCREEN_WITH_PARAMS, title = "Character Detail")
    object Quizzes : Screens("quizzes", "Quizzes")
    object Settings : Screens("settings", "Settings")
}