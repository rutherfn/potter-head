package com.nicholas.rutherford.potter.head.entry.point.navigation

sealed class Screens(val route: String, val title: String) {
    object Characters : Screens("characters", "Characters")
    object Quizzes : Screens("quizzes", "Quizzes")
    object Settings : Screens("settings", "Settings")
}