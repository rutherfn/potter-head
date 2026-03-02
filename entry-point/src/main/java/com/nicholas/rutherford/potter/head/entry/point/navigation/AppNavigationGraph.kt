package com.nicholas.rutherford.potter.head.entry.point.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.nicholas.rutherford.potter.head.base.view.model.LocalViewModelFactory
import com.nicholas.rutherford.potter.head.base.view.model.ObserveLifecycle
import com.nicholas.rutherford.potter.head.entry.point.navigation.appbar.AppBar
import com.nicholas.rutherford.potter.head.feature.characters.characterdetail.CharacterDetailParams
import com.nicholas.rutherford.potter.head.feature.characters.characterdetail.CharacterDetailScreen
import com.nicholas.rutherford.potter.head.feature.characters.characterdetail.CharacterDetailViewModel
import com.nicholas.rutherford.potter.head.feature.characters.characterfilters.CharacterFiltersParams
import com.nicholas.rutherford.potter.head.feature.characters.characterfilters.CharacterFiltersScreen
import com.nicholas.rutherford.potter.head.feature.characters.characterfilters.CharacterFiltersViewModel
import com.nicholas.rutherford.potter.head.feature.characters.characters.CharactersParams
import com.nicholas.rutherford.potter.head.feature.characters.characters.CharactersScreen
import com.nicholas.rutherford.potter.head.feature.characters.characters.CharactersViewModel
import com.nicholas.rutherford.potter.head.feature.quizzes.QuizzesParams
import com.nicholas.rutherford.potter.head.feature.quizzes.QuizzesScreen
import com.nicholas.rutherford.potter.head.feature.quizzes.QuizzesViewModel
import com.nicholas.rutherford.potter.head.feature.settings.SettingsParams
import com.nicholas.rutherford.potter.head.feature.settings.SettingsScreen
import com.nicholas.rutherford.potter.head.feature.settings.SettingsViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

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
     * Holds the currently displayed AppBar as a StateFlow.
     * Should be observed and rendered from the top-level UI NavigationComponent.
     */
    private val _currentAppBar = MutableStateFlow<AppBar?>(null)
    val currentAppBar: StateFlow<AppBar?> = _currentAppBar.asStateFlow()

    /**
     * Call this function from within a screen to set or update the AppBar.
     *
     * @param appBar The [AppBar] to display or null to hide the app bar.
     */
    fun updateAppBar(appBar: AppBar?) {
        _currentAppBar.value = appBar
    }

    /**
     * Composable function that manages the app bar lifecycle for a screen.
     * Observes the back stack entry's lifecycle and updates the app bar when the screen becomes active.
     *
     * @param backStackEntry The navigation back stack entry for the current screen.
     * @param appBarProvider A lambda that creates the [AppBar] to display for this screen.
     *
     * @author Nicholas Rutherford
     */
    @Composable
    fun ManageAppBarLifecycle(
        backStackEntry: NavBackStackEntry,
        appBarProvider: () -> AppBar
    ) {
        DisposableEffect(backStackEntry) {
            val observer = LifecycleEventObserver { _, event ->
                if (event == Lifecycle.Event.ON_RESUME) {
                    updateAppBar(appBar = appBarProvider())
                }
            }
            backStackEntry.lifecycle.addObserver(observer)

            // Set app bar immediately if already resumed
            if (backStackEntry.lifecycle.currentState.isAtLeast(Lifecycle.State.RESUMED)) {
                updateAppBar(appBar = appBarProvider())
            }
            onDispose {
                backStackEntry.lifecycle.removeObserver(observer)
            }
        }
    }

    /**
     * Defines the characters screen in the navigation graph.
     *
     * This function sets up the characters list screen with:
     * - Route: Characters.route
     * - ViewModel: [CharactersViewModel] created via [LocalViewModelFactory]
     * - Screen: [CharactersScreen] with parameters for character click handling
     *
     * The ViewModel is scoped to the navigation graph, so it will be retained
     * when navigating between screens within the same graph.
     */
    fun NavGraphBuilder.charactersScreen() {
        composable(route = Screens.Characters.route) { backStackEntry ->

            val factory = LocalViewModelFactory.current
            val viewModel: CharactersViewModel = viewModel(factory = factory)
            val appBarFactory = LocalAppBarFactory.current
            val state = viewModel.charactersStateFlow.collectAsState().value

            ObserveLifecycle(viewModel = viewModel)
            
            ManageAppBarLifecycle(
                backStackEntry = backStackEntry,
                appBarProvider = { appBarFactory.createCharactersAppBar() }
            )

            CharactersScreen(
                params = CharactersParams(
                    state = state,
                    onCharacterClicked = { characterName -> viewModel.onCharacterClicked(characterName) },
                    onRetryClicked = { viewModel.retryLoadingCharacters() },
                    onLoadMore = { viewModel.loadMoreCharacters() },
                    buildCharacterStatusIds = { character -> viewModel.buildCharacterStatusIds(character) },
                    onSearchQueryChange = { query -> viewModel.onSearchQueryChange(query) },
                    onClearClicked = { viewModel.onClearClicked() },
                    onFilterClicked = { viewModel.onFilterClicked() }
                )
            )
        }
    }

    /**
     * Defines the character filters screen in the navigation graph.
     *
     * This function sets up the character filters screen with:
     * - Route: CharacterFilters.route
     * - ViewModel: [CharacterFiltersViewModel] created via [LocalViewModelFactory]
     * - Screen: [CharacterFiltersScreen] with parameters for character click handling
     *
     * The ViewModel is scoped to the navigation graph, so it will be retained
     * when navigating between screens within the same graph.
     */
    fun NavGraphBuilder.charactersFiltersScreen() {
        composable(route = Screens.CharacterFilters.route) { backStackEntry ->

            val factory = LocalViewModelFactory.current
            val viewModel: CharacterFiltersViewModel = viewModel(factory = factory)
            val appBarFactory = LocalAppBarFactory.current
            val state = viewModel.characterFiltersStateFlow.collectAsState().value

            ObserveLifecycle(viewModel = viewModel)

            ManageAppBarLifecycle(
                backStackEntry = backStackEntry,
                appBarProvider = { 
                    appBarFactory.createFiltersAppBar(
                        onIconButtonClicked = { viewModel.onBackClicked() }
                    )
                }
            )

            CharacterFiltersScreen(
                params = CharacterFiltersParams(
                    state = state,
                    houses = viewModel.buildHouses(),
                    onFilterHouseClicked = { type, value -> viewModel.onFilterHouseClicked(type, value) }
                )
            )
        }
    }

    /**
     * Defines the character detail screen in the navigation graph.
     *
     * This function sets up the character detail screen with:
     * - Route: CharactersDetail.route (includes ID parameter)
     * - Arguments: [NavArguments.characterDetail] (defines the ID string parameter)
     * - ViewModel: [CharacterDetailViewModel] scoped to the back stack entry
     * - Screen: [CharacterDetailScreen] with state from the ViewModel
     *
     * The ViewModel is scoped to the back stack entry to ensure it survives
     * configuration changes and is properly cleared when the screen is removed
     * from the back stack.
     *
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
     * - Route: Quizzes.route
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
     * - Route: Settings.route
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

    /**
     * List of all screen setup functions to be included in the navigation graph.
     *
     * This list defines all screens that should be registered in the navigation graph.
     * To add a new screen, simply add its setup function to this list.
     *
     * The order of screens in this list does not affect navigation behavior,
     * but it's recommended to keep them in a logical order for maintainability.
     */
    private val screenSetupFunctions: List<NavGraphBuilder.() -> Unit> = listOf(
        { charactersScreen() },
        { charactersFiltersScreen() },
        { characterDetailScreen() },
        { quizzesScreen() },
        { settingsScreen() }
    )

    fun setupAllScreens(builder: NavGraphBuilder) {
        with(this@AppNavigationGraph) {
            screenSetupFunctions.forEach { setupFunction ->
                builder.setupFunction()
            }
        }
    }
}