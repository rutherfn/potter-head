package com.nicholas.rutherford.potter.head.entry.point.navigation

import androidx.compose.runtime.collectAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.nicholas.rutherford.potter.head.base.view.model.LocalViewModelFactory
import com.nicholas.rutherford.potter.head.core.Constants
import com.nicholas.rutherford.potter.head.feature.characters.characterdetail.CharacterDetailParams
import com.nicholas.rutherford.potter.head.feature.characters.characterdetail.CharacterDetailScreen
import com.nicholas.rutherford.potter.head.feature.characters.characterdetail.CharacterDetailViewModel
import com.nicholas.rutherford.potter.head.feature.characters.characters.CharactersParams
import com.nicholas.rutherford.potter.head.feature.characters.characters.CharactersScreen
import com.nicholas.rutherford.potter.head.feature.characters.characters.CharactersViewModel
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

    fun NavGraphBuilder.characterDetailScreen() {
        composable(
            route = Screens.CharactersDetail.route,
            arguments = NavArguments.characterDetail
        ) { backStackEntry ->
            val factory = LocalViewModelFactory.current
            
            // Debug: Extract and log navigation arguments from backStackEntry
            val characterId = backStackEntry.arguments?.getString(Constants.NamedArguments.ID)
            println("DEBUG AppNavigationGraph: Character ID from backStackEntry.arguments: $characterId")
            println("DEBUG AppNavigationGraph: All arguments: ${backStackEntry.arguments?.keySet()?.joinToString()}")
            
            // The viewModel() composable automatically provides SavedStateHandle with navigation arguments
            // when used with a NavBackStackEntry. We explicitly pass viewModelStoreOwner to ensure
            // the SavedStateHandle is correctly associated with the navigation entry.
            // The factory will extract it from CreationExtras and use the CharacterDetailViewModelFactory
            // (with assisted injection pattern) to create the ViewModel.
            val viewModel: CharacterDetailViewModel = viewModel(
                factory = factory,
                viewModelStoreOwner = backStackEntry
            )

            CharacterDetailScreen(
                params = CharacterDetailParams(
                    state = viewModel.characterDetailStateFlow.collectAsState().value
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