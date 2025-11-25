package com.nicholas.rutherford.potter.head.core

object Constants {
    const val BASE_API_URL = "https://hp-api.onrender.com/api/"
    const val APP_GRAPH_METRO_GRAPH_CLASS_NAME = "com.nicholas.rutherford.potter.head.di.AppGraph$$\$MetroGraph"
    const val NETWORK_MODULE_CLASS_NAME = "com.nicholas.rutherford.potter.head.network.di.NetworkModule"
    const val NETWORK_MODULE_METRO_GRAPH_CLASS_NAME = "com.nicholas.rutherford.potter.head.network.di.NetworkModule$$\$MetroGraph"
    const val NETWORK_MODULE_NAME = "NetworkModule"
    const val SAVED_STATE_MODULE_NAME = "SavedStateModule"
    const val SAVED_STATE_MODULE_CLASS_NAME =
        "com.nicholas.rutherford.potter.head.saved.state.di.SavedStateModule"
    const val SAVED_STATE_MODULE_METRO_GRAPH_CLASS_NAME =
        "com.nicholas.rutherford.potter.head.saved.state.di.SavedStateModule$$\$MetroGraph"
    const val NAVIGATOR_MODULE_NAME = "NavigatorModule"

    const val NAVIGATOR_MODULE_CLASS_NAME =
        "com.nicholas.rutherford.potter.head.navigation.di.NavigatorModule"
    const val NAVIGATOR_MODULE_METRO_GRAPH_CLASS_NAME =
        "com.nicholas.rutherford.potter.head.navigation.di.NavigatorModule$$\$MetroGraph"

    object NavigationDestinations {
        const val CHARACTERS_SCREEN = "charactersScreen"
        const val CHARACTER_DETAIL_SCREEN = "characterDetailScreen"
        const val CHARACTER_DETAIL_SCREEN_WITH_PARAMS = "$CHARACTER_DETAIL_SCREEN/{id}"
        const val QUIZZES_SCREEN = "quizzesScreen"
        const val QUIZ_SELECTED_DETAIL_SCREEN = "quizSelectedDetailScreen" // pass in a name
        const val TOUR_THE_APP_SCREEN = "tourTheAppScreen" // pass in nothing
        const val SETTINGS_SCREEN = "settingsScreen"
    }

    object ScreenTitles {
        const val CHARACTERS = "Characters"
        const val CHARACTER_DETAIL = "Details"
        const val QUIZZES = "Quizzes"
        const val SETTINGS = "Settings"
    }

    object NamedArguments {
        const val ID = "id"
    }
}