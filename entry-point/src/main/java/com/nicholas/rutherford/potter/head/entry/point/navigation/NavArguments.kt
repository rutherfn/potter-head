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
    val characterDetail = listOf(navArgument(Constants.NamedArguments.CHARACTER_NAME) { type = NavType.StringType })
    
    val quizDetail = listOf(
        navArgument(Constants.NamedArguments.QUIZ_NAME) { type = NavType.StringType },
        navArgument(Constants.NamedArguments.QUIZ_DESCRIPTION) { type = NavType.StringType },
        navArgument(Constants.NamedArguments.QUIZ_IMAGE_URL) { type = NavType.StringType }
    )

    val takeQuiz = listOf(navArgument(name = Constants.NamedArguments.QUIZ_NAME) { type = NavType.StringType })

    val quizResult = listOf(navArgument(name = Constants.NamedArguments.QUIZ_ID) { type = NavType.StringType})


}