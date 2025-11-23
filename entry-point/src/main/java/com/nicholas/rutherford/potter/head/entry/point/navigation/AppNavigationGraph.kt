package com.nicholas.rutherford.potter.head.entry.point.navigation

import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.nicholas.rutherford.potter.head.base.view.model.LocalViewModelFactory
import com.nicholas.rutherford.potter.head.feature.characters.CharactersParams
import com.nicholas.rutherford.potter.head.feature.characters.CharactersScreen
import com.nicholas.rutherford.potter.head.feature.characters.CharactersViewModel
import com.nicholas.rutherford.potter.head.feature.quizzes.QuizzesParams
import com.nicholas.rutherford.potter.head.feature.quizzes.QuizzesScreen
import com.nicholas.rutherford.potter.head.feature.quizzes.QuizzesViewModel
import com.nicholas.rutherford.potter.head.feature.settings.SettingsParams
import com.nicholas.rutherford.potter.head.feature.settings.SettingsScreen
import com.nicholas.rutherford.potter.head.feature.settings.SettingsViewModel

object AppNavigationGraph {

    fun NavGraphBuilder.charactersScreen() {
        composable(route = Screens.Characters.route) {

            val factory = LocalViewModelFactory.current
            val viewModel: CharactersViewModel = viewModel(factory = factory)

            CharactersScreen(
                params = CharactersParams(
                    onCharacterClicked = { viewModel.onCharacterClicked() }
                )
            )
        }
    }

    fun NavGraphBuilder.quizzesScreen() {
        composable(route = Screens.Quizzes.route) {

            val factory = LocalViewModelFactory.current
            val viewModel: QuizzesViewModel = viewModel(factory = factory)

            QuizzesScreen(
                params = QuizzesParams(
                    onSortingHatQuizClicked = { viewModel.onSortingHatQuizClicked() }
                )
            )
        }
    }

    fun NavGraphBuilder.settingsScreen() {
        composable(route = Screens.Settings.route) {

            val factory = LocalViewModelFactory.current
            val viewModel: SettingsViewModel = viewModel(factory = factory)

            SettingsScreen(
                params = SettingsParams(
                    onItemClicked = { viewModel.onItemClicked() }
                )
            )
        }
    }
}