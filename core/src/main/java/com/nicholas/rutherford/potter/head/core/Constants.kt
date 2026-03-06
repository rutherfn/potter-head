package com.nicholas.rutherford.potter.head.core

object Constants {
    const val CHECKMARK = "✓"
    const val DATABASE_NAME = "potter_head_database"
    const val MALE = "male"
    const val FEMALE = "female"
    const val GRYFFINDOR_HOUSE = "gryffindor"
    const val GRYFFINDOR_HOUSE_URL = "https://thenichollsworth.com/wp-content/uploads/2020/11/C0441055-AEE4-4C0D-8F43-A708DDEB6C3B.jpeg"
    const val HUFFLEPUFF_HOUSE = "hufflepuff"
    const val HUFFLEPUFF_HOUSE_URL = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQQgWJon5IQx6E_koDwYjuHXwPGEBPY3Jj_hg&s"
    const val SLYTHERIN_HOUSE = "slytherin"
    const val SLYTHERIN_HOUSE_URL = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQq8004tQqlFrUIQUciLx9rPllG_92LZnnyqw&s"
    const val RAVENCLAW_HOUSE = "ravenclaw"
    const val RAVENCLAW_HOUSE_URL = "https://m.media-amazon.com/images/I/81TLC+duJ0L.jpg"
    const val NO_HOUSE_FILTER = "no_house"
    const val INITIAL_PAGE_SIZE = 20
    const val DELAY_LOADING_MORE_CHARACTERS = 300L
    const val SHIMMER_CHARACTER_COUNT = 20
    const val RETRY_LOADING_CHARACTERS_DELAY = 2000L
    const val BASE_API_URL = "https://hp-api.onrender.com/api/"
    const val NETWORK_MODULE_CLASS_NAME = "com.nicholas.rutherford.potter.head.network.di.NetworkModule"
    const val NETWORK_MODULE_NAME = "NetworkModule"
    const val NAVIGATOR_MODULE_NAME = "NavigatorModule"
    const val NAVIGATOR_MODULE_CLASS_NAME =
        "com.nicholas.rutherford.potter.head.navigation.di.NavigatorModule"

    object NavigationDestinations {
        const val CHARACTERS_SCREEN = "charactersScreen"
        const val CHARACTERS_FILTERS_SCREEN = "charactersFiltersScreen"
        const val CHARACTER_DETAIL_SCREEN = "characterDetailScreen"
        const val CHARACTER_DETAIL_SCREEN_WITH_PARAMS = "$CHARACTER_DETAIL_SCREEN/{id}"
        const val QUIZZES_SCREEN = "quizzesScreen"
        const val QUIZ_SELECTED_DETAIL_SCREEN = "quizSelectedDetailScreen"
        const val TOUR_THE_APP_SCREEN = "tourTheAppScreen"
        const val SETTINGS_SCREEN = "settingsScreen"
    }

    object ScreenTitles {
        const val CHARACTERS = "Characters"
        const val CHARACTERS_FILTERS = "Filters"
        const val CHARACTER_DETAIL = "Details"
        const val QUIZZES = "Quizzes"
        const val SETTINGS = "Settings"
    }

    object NamedArguments {
        const val ID = "id"
    }
}