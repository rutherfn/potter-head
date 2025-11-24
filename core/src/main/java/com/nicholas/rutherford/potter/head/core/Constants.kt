package com.nicholas.rutherford.potter.head.core

object Constants {
    const val BASE_API_URL = "https://hp-api.onrender.com/api/"
    const val APP_GRAPH_METRO_GRAPH_CLASS_NAME = "com.nicholas.rutherford.potter.head.di.AppGraph$$\$MetroGraph"
    const val NETWORK_MODULE_CLASS_NAME = "com.nicholas.rutherford.potter.head.network.di.NetworkModule"
    const val NETWORK_MODULE_METRO_GRAPH_CLASS_NAME = "com.nicholas.rutherford.potter.head.network.di.NetworkModule$$\$MetroGraph"
    const val NETWORK_MODULE_NAME = "NetworkModule"

    /**
     * Name of the SavedStateModule for use in error messages and logging.
     */
    const val SAVED_STATE_MODULE_NAME = "SavedStateModule"

    /**
     * Full class name of the SavedStateModule interface.
     */
    const val SAVED_STATE_MODULE_CLASS_NAME =
        "com.nicholas.rutherford.potter.head.saved.state.di.SavedStateModule"

    /**
     * Full class name of the Metro-generated SavedStateModule implementation.
     * Metro generates SavedStateModule$$$MetroGraph as the implementation class.
     */
    const val SAVED_STATE_MODULE_METRO_GRAPH_CLASS_NAME =
        "com.nicholas.rutherford.potter.head.saved.state.di.SavedStateModule$$\$MetroGraph"

    /**
     * Name of the NavigatorModule for use in error messages and logging.
     */
    const val NAVIGATOR_MODULE_NAME = "NavigatorModule"

    /**
     * Full class name of the NavigatorModule interface.
     */
    const val NAVIGATOR_MODULE_CLASS_NAME =
        "com.nicholas.rutherford.potter.head.navigation.di.NavigatorModule"

    /**
     * Full class name of the Metro-generated NavigatorModule implementation.
     * Metro generates NavigatorModule$$$MetroGraph as the implementation class.
     */
    const val NAVIGATOR_MODULE_METRO_GRAPH_CLASS_NAME =
        "com.nicholas.rutherford.potter.head.navigation.di.NavigatorModule$$\$MetroGraph"

    object NavigationDestinations {
        const val CHARACTER_DETAIL_SCREEN = "characterDetail"
        const val CHARACTER_DETAIL_SCREEN_WITH_PARAMS = "$CHARACTER_DETAIL_SCREEN/{id}"
        const val QUIZ_SELECTED_DETAIL_SCREEN = "quizSelectedDetailScreen" // pass in a name
        const val TOUR_THE_APP_SCREEN = "tourTheAppScreen" // pass in nothing
    }

    object NamedArguments {
        const val ID = "id"
    }
}