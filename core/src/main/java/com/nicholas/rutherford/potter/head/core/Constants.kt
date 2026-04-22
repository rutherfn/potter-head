package com.nicholas.rutherford.potter.head.core

object Constants {
    const val IS_NOT_ALIVE_FILTER = "isNotAliveFilter"
    const val IS_ALIVE_FILTER = "isAliveFilter"
    const val IS_WIZARD_FILTER = "isWizardFilter"
    const val IS_NOT_WIZARD_FILTER = "isNotWizardFilter"
    const val HAS_HOUSE_AFFILIATION_FILTER = "hasHouseAffiliationFilter"
    const val HAS_NOT_HOUSE_AFFILIATION_FILTER = "hasNotHouseAffiliationFilter"
    const val CHECKMARK = "✓"
    const val DATABASE_NAME = "potter_head_database"
    const val MALE = "male"
    const val FEMALE = "female"
    const val SPECIES_ACROMANTULA = "acromantula"
    const val SPECIES_CAT = "cat"
    const val SPECIES_CENTAUR = "centaur"
    const val SPECIES_CEPHALOPOD = "cephalopod"
    const val SPECIES_DOG = "dog"
    const val SPECIES_DRAGON = "dragon"
    const val SPECIES_GHOST = "ghost"
    const val SPECIES_GIANT = "giant"
    const val SPECIES_GOBLIN = "goblin"
    const val SPECIES_HALF_GIANT = "half-giant"
    const val SPECIES_HALF_HUMAN = "half-human"
    const val SPECIES_HAT = "hat"
    const val SPECIES_HIPPOGRIFF = "hippogriff"
    const val SPECIES_HOUSE_ELF = "house-elf"
    const val SPECIES_HUMAN = "human"
    const val SPECIES_OWL = "owl"
    const val SPECIES_PHOENIX = "phoenix"
    const val SPECIES_POLTERGEIST = "poltergeist"
    const val SPECIES_PYGMY_PUFF = "pygmy puff"
    const val SPECIES_SELKIE = "selkie"
    const val SPECIES_SERPENT = "serpent"
    const val SPECIES_SNAKE = "snake"
    const val SPECIES_THREE_HEADED_DOG = "three-headed dog"
    const val SPECIES_TOAD = "toad"
    const val SPECIES_VAMPIRE = "vampire"
    const val SPECIES_WEREWOLF = "werewolf"
    
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
        const val SPELLS_SCREEN = "spellsScreen"
        const val CHARACTERS_FILTERS_SCREEN = "charactersFiltersScreen"
        const val CHARACTER_DETAIL_SCREEN = "characterDetailScreen"
        const val QUIZ_DETAIL_SCREEN = "quizDetailScreen"
        const val TAKE_QUIZ_SCREEN = "takeQuizScreen"
        const val QUIZ_RESULT_SCREEN = "quizResultScreen"
        const val CHARACTER_DETAIL_SCREEN_WITH_PARAMS = "$CHARACTER_DETAIL_SCREEN/{name}"
        const val QUIZ_DETAIL_SCREEN_WITH_PARAMS = "$QUIZ_DETAIL_SCREEN/{quiz_name}/{quiz_description}/{QUIZ_IMAGE_URL}"
        const val TAKE_QUIZ_SCREEN_WITH_PARAMS = "$TAKE_QUIZ_SCREEN/{quiz_name}"
        const val QUIZ_RESULT_SCREEN_WITH_PARAMS = "$QUIZ_RESULT_SCREEN/{quiz_id}"
        const val QUIZZES_SCREEN = "quizzesScreen"
        const val QUIZ_SELECTED_DETAIL_SCREEN = "quizSelectedDetailScreen"
        const val TOUR_THE_APP_SCREEN = "tourTheAppScreen"
        const val SETTINGS_SCREEN = "settingsScreen"
    }

    object ScreenTitles {
        const val CHARACTERS = "Characters"
        const val SPELLS = "Spells"
        const val CHARACTERS_FILTERS = "Filters"
        const val CHARACTER_DETAIL = "Details"
        const val QUIZZES = "Quizzes"
        const val TAKE_QUIZ = "TakeQuiz"
        const val QUIZ_DETAIL = "QuizDetail"
        const val QUIZ_RESULT = "QuizResult"
        const val SETTINGS = "Settings"
    }

    object NamedArguments {
        const val QUIZ_NAME = "quiz_name"
        const val QUIZ_ID = "quiz_id"
        const val QUIZ_DESCRIPTION = "quiz_description"
        const val QUIZ_IMAGE_URL = "QUIZ_IMAGE_URL"
        const val CHARACTER_NAME = "name"
    }
}
