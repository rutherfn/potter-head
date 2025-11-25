package com.nicholas.rutherford.potter.head.entry.point.navigation

import androidx.compose.runtime.collectAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.NavHost
import com.nicholas.rutherford.potter.head.base.view.model.LocalViewModelFactory
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

/**
 * Object providing extension functions to build the application's navigation graph.
 *
 * This object contains extension functions on [NavGraphBuilder] that define all
 * composable screens in the application. Each function:
 * - Defines a composable route using [Screens]
 * - Creates the appropriate ViewModel using [LocalViewModelFactory]
 * - Composes the corresponding feature screen with its required parameters
 *
 * The navigation graph is built by calling these extension functions within a
 * [NavGraphBuilder] scope, typically in a [NavHost] composable.
 *
 * @see Screens for available navigation routes
 * @see NavArguments for navigation argument definitions
 *
 * @author Nicholas Rutherford
 */
object AppNavigationGraph {

    /**
     * Defines the characters screen in the navigation graph.
     *
     * This function sets up the characters list screen with:
     * - Route: [com.nicholas.rutherford.potter.head.entry.point.navigation.Screens.Characters.route]
     * - ViewModel: [CharactersViewModel] created via [LocalViewModelFactory]
     * - Screen: [CharactersScreen] with parameters for character click handling
     *
     * The ViewModel is scoped to the navigation graph, so it will be retained
     * when navigating between screens within the same graph.
     */
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

    /**
     * Defines the character detail screen in the navigation graph.
     *
     * This function sets up the character detail screen with:
     * - Route: [Screens.CharactersDetail.route] (includes ID parameter)
     * - Arguments: [NavArguments.characterDetail] (defines the ID string parameter)
     * - ViewModel: [CharacterDetailViewModel] scoped to the back stack entry
     * - Screen: [CharacterDetailScreen] with state from the ViewModel
     *
     * The ViewModel is scoped to the back stack entry to ensure it survives
     * configuration changes and is properly cleared when the screen is removed
     * from the back stack.
     *
     * @param backStackEntry The navigation back stack entry for this screen,
     * used to scope the ViewModel lifecycle.
     */
    fun NavGraphBuilder.characterDetailScreen() {
        composable(
            route = Screens.CharactersDetail.route,
            arguments = NavArguments.characterDetail
        ) { backStackEntry ->
            val factory = LocalViewModelFactory.current
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

    /**
     * Defines the quizzes screen in the navigation graph.
     *
     * This function sets up the quizzes list screen with:
     * - Route: [Screens.Quizzes.route]
     * - ViewModel: [QuizzesViewModel] created via [LocalViewModelFactory]
     * - Screen: [QuizzesScreen] with parameters for quiz click handling
     *
     * The ViewModel is scoped to the navigation graph, so it will be retained
     * when navigating between screens within the same graph.
     */
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

    /**
     * Defines the settings screen in the navigation graph.
     *
     * This function sets up the settings screen with:
     * - Route: [Screens.Settings.route]
     * - ViewModel: [SettingsViewModel] created via [LocalViewModelFactory]
     * - Screen: [SettingsScreen] with parameters for settings item click handling
     *
     * The ViewModel is scoped to the navigation graph, so it will be retained
     * when navigating between screens within the same graph.
     */
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