package com.nicholas.rutherford.potter.head.entry.point.navigation

import androidx.activity.compose.BackHandler
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
import com.nicholas.rutherford.potter.head.feature.quizzes.quizdetail.QuizDetailParams
import com.nicholas.rutherford.potter.head.feature.quizzes.quizdetail.QuizDetailScreen
import com.nicholas.rutherford.potter.head.feature.quizzes.quizdetail.QuizDetailViewModel
import com.nicholas.rutherford.potter.head.feature.quizzes.quizresult.QuizResultParams
import com.nicholas.rutherford.potter.head.feature.quizzes.quizresult.QuizResultScreen
import com.nicholas.rutherford.potter.head.feature.quizzes.quizresult.QuizResultViewModel
import com.nicholas.rutherford.potter.head.feature.quizzes.takequiz.TakeQuizParams
import com.nicholas.rutherford.potter.head.feature.quizzes.takequiz.TakeQuizScreen
import com.nicholas.rutherford.potter.head.feature.quizzes.takequiz.TakeQuizViewModel
import com.nicholas.rutherford.potter.head.feature.settings.SettingsParams
import com.nicholas.rutherford.potter.head.feature.settings.SettingsScreen
import com.nicholas.rutherford.potter.head.feature.settings.SettingsViewModel
import com.nicholas.rutherford.potter.head.feature.spells.SpellsParams
import com.nicholas.rutherford.potter.head.feature.spells.SpellsScreen
import com.nicholas.rutherford.potter.head.feature.spells.SpellsViewModel
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

            if (backStackEntry.lifecycle.currentState.isAtLeast(Lifecycle.State.RESUMED)) {
                updateAppBar(appBar = appBarProvider())
            }
            onDispose {
                backStackEntry.lifecycle.removeObserver(observer)
            }
        }
    }

    /**
     * Defines the spells screen in the navigation graph.
     *
     * This function sets up the spells screen with:
     * - Route: Characters.route
     * - ViewModel: [SpellsViewModel] created via [LocalViewModelFactory]
     * - Screen: [SpellsScreen] with parameters.
     *
     * The ViewModel is scoped to the navigation graph, so it will be retained
     * when navigating between screens within the same graph.
     */
    fun NavGraphBuilder.spellsScreen() {
        composable(route = Screens.Spells.route) { backStackEntry ->

            val factory = LocalViewModelFactory.current
            val viewModel: SpellsViewModel = viewModel<SpellsViewModel>(factory = factory)
            val appBarFactory = LocalAppBarFactory.current
            val state = viewModel.spellsStateFlow.collectAsState().value

            ObserveLifecycle(viewModel = viewModel)

            ManageAppBarLifecycle(
                backStackEntry = backStackEntry,
                appBarProvider = { appBarFactory.createSpellsAppBar() }
            )

            SpellsScreen(params = SpellsParams(
                state = state,
                onRetryClicked = { viewModel.retryLoadingSpells() },
                onSearchQueryChange = { query -> viewModel.onSearchQueryChange(query = query) },
                onClearClicked = { viewModel.onClearClicked() }
            ))
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
            val viewModel: CharactersViewModel = viewModel<CharactersViewModel>(factory = factory)
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
                    onCharacterClicked = { name -> viewModel.onCharacterClicked(name) },
                    onRetryClicked = { viewModel.retryLoadingCharacters() },
                    onLoadMore = { viewModel.loadMoreCharacters() },
                    buildCharacterStatusIds = { character -> viewModel.buildCharacterStatusIds(character) },
                    onSearchQueryChange = { query -> viewModel.onSearchQueryChange(query) },
                    onClearClicked = { viewModel.onClearClicked() },
                    onFilterClicked = { viewModel.onFilterClicked() },
                    onClearFiltersClicked = { viewModel.onClearFiltersClicked() }
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
            val viewModel: CharacterFiltersViewModel = viewModel<CharacterFiltersViewModel>(factory = factory)
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
                    genders = viewModel.buildGenders(),
                    species = viewModel.buildSpecies(),
                    hogwartsAffiliations = viewModel.buildHogwartsAffiliations(),
                    wizardStatuses = viewModel.buildWizardStatuses(),
                    aliveStatuses = viewModel.buildAliveStatuses(),
                    onFilterHouseClicked = { value -> viewModel.onFilterHouseClicked(value) },
                    onFilterGenderClicked = { value -> viewModel.onFilterGenderClicked(value) },
                    onFilterSpeciesClicked = { value -> viewModel.onFilterSpeciesClicked(value) },
                    onFilterHogwartsAffiliationClicked = { value -> viewModel.onFilterHogwartsAffiliationClicked(value) },
                    onFilterWizardStatusClicked = { value -> viewModel.onFilterWizardStatusClicked(value) },
                    onFilterAliveStatusClicked = { value -> viewModel.onFilterAliveStatusClicked(value) }
                )
            )
        }
    }

    /**
     * Defines the quiz detail screen in the navigation graph.
     *
     * This function sets up the quiz detail screen with:
     * - Route: QuizDetail.route (includes name, description, and imageUrl parameters)
     * - Arguments: [NavArguments.quizDetail] (defines the string parameters)
     * - ViewModel: [QuizDetailViewModel] scoped to the back stack entry
     * - Screen: [QuizDetailScreen] with state from the ViewModel
     *
     * The ViewModel is scoped to the back stack entry to ensure it survives
     * configuration changes and is properly cleared when the screen is removed
     * from the back stack.
     */
    fun NavGraphBuilder.quizDetailScreen() {
        composable(
            route = Screens.QuizDetail.route,
            arguments = NavArguments.quizDetail
        ) { backStackEntry ->
            val factory = LocalViewModelFactory.current
            val viewModel: QuizDetailViewModel = viewModel<QuizDetailViewModel>(
                factory = factory,
                viewModelStoreOwner = backStackEntry
            )
            val appBarFactory = LocalAppBarFactory.current

            ObserveLifecycle(viewModel = viewModel)

            ManageAppBarLifecycle(
                backStackEntry = backStackEntry,
                appBarProvider = {
                    appBarFactory.createQuizDetailAppBar(
                        onIconButtonClicked = { viewModel.onBackClicked() }
                    )
                }
            )

            QuizDetailScreen(
                params = QuizDetailParams(
                    state = viewModel.quizDetailStateFlow.collectAsState().value,
                    onStartQuizClicked = { title -> viewModel.onStartQuizClicked(title) }
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
            val viewModel: CharacterDetailViewModel = viewModel<CharacterDetailViewModel>(
                factory = factory,
                viewModelStoreOwner = backStackEntry
            )
            val appBarFactory = LocalAppBarFactory.current

            ObserveLifecycle(viewModel = viewModel)

            ManageAppBarLifecycle(
                backStackEntry = backStackEntry,
                appBarProvider = {
                    appBarFactory.createCharacterDetailAppBar(
                        onIconButtonClicked = { viewModel.onBackClicked() }
                    )
                }
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
        composable(route = Screens.Quizzes.route) { backStackEntry ->

            val factory = LocalViewModelFactory.current
            val viewModel: QuizzesViewModel = viewModel<QuizzesViewModel>(factory = factory)
            val appBarFactory = LocalAppBarFactory.current

            ObserveLifecycle(viewModel = viewModel)

            ManageAppBarLifecycle(
                backStackEntry = backStackEntry,
                appBarProvider = { appBarFactory.createQuizzesAppBar() }
            )

            QuizzesScreen(
                params = QuizzesParams(
                    state = viewModel.quizzesStateFlow.collectAsState().value,
                    onQuizClicked = { title, description, imageUrl -> viewModel.onQuizClicked(title, description, imageUrl) },
                    onFilterItemClicked = { index -> viewModel.onFilterItemClicked(index) },
                    onRetryClicked = { viewModel.retryLoadingQuizzes() }
                )
            )
        }
    }

    /**
     * Defines the quiz result screen in the navigation graph.
     *
     * This function sets up the quiz result screen with:
     * - Route: QuizResult.route
     * - ViewModel: [QuizResultViewModel] created via [LocalViewModelFactory]
     * - Screen: [QuizResultScreen] with parameters for quiz click handling
     *
     * The ViewModel is scoped to the navigation graph, so it will be retained
     * when navigating between screens within the same graph.
     */
    fun NavGraphBuilder.quizResultScreen() {
        composable(
            route = Screens.QuizResult.route,
            arguments = NavArguments.quizResult
        ) { backStackEntry ->
            val factory = LocalViewModelFactory.current
            val viewModel: QuizResultViewModel = viewModel<QuizResultViewModel>(
                factory = factory,
                viewModelStoreOwner = backStackEntry
            )
            val state = viewModel.quizResultStateFlow.collectAsState().value
            val appBarFactory = LocalAppBarFactory.current

            ObserveLifecycle(viewModel = viewModel)

            ManageAppBarLifecycle(
                backStackEntry = backStackEntry,
                appBarProvider = {
                    appBarFactory.createQuizResultAppBar(
                        onIconButtonClicked = { viewModel.onBackClicked() }
                    )
                }
            )

            QuizResultScreen(
                params = QuizResultParams(
                    state = state,
                    onViewResultsClicked = { viewModel.onViewResultsClicked() },
                    onHideResultsClicked = { viewModel.onHideResultsClicked() },
                    onContinueClicked = { viewModel.onContinueClicked() }
                )
            )
        }
    }

    /**
     * Defines the Take Quiz screen in the navigation graph.
     *
     * This function sets up the take quiz screen with:
     * - Route: TakeAQuiz.route
     * - ViewModel: [TakeQuizViewModel] created via [LocalViewModelFactory]
     * - Screen: [TakeQuizScreen] with parameters for quiz click handling
     *
     * The ViewModel is scoped to the navigation graph, so it will be retained
     * when navigating between screens within the same graph.
     */
    fun NavGraphBuilder.takeQuizScreen() {
        composable(
            route = Screens.TakeQuiz.route,
            arguments = NavArguments.takeQuiz
        ) { backStackEntry ->
            val factory = LocalViewModelFactory.current
            val viewModel: TakeQuizViewModel = viewModel<TakeQuizViewModel>(
                factory = factory,
                viewModelStoreOwner = backStackEntry
            )
            val state = viewModel.takeQuizStateFlow.collectAsState().value
            val appBarFactory = LocalAppBarFactory.current

            ObserveLifecycle(viewModel = viewModel)

            ManageAppBarLifecycle(
                backStackEntry = backStackEntry,
                appBarProvider = {
                    appBarFactory.createTakeQuizAppBar(
                        onIconButtonClicked = { viewModel.onBackClicked() }
                    )
                }
            )

            BackHandler(onBack = { viewModel.onBackClicked() })

            TakeQuizScreen(
                params = TakeQuizParams(
                    state = state,
                    onAnswerSelected = { answerIndex -> viewModel.onAnswerSelected(answerIndex) },
                    onContinueClicked = { currentQuestionNumber, questionSize, selectedAnswerIndex -> viewModel.onContinueClicked(
                        currentQuestionNumber = currentQuestionNumber,
                        questionSize = questionSize,
                        selectedAnswerIndex = selectedAnswerIndex
                    ) }
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
            val viewModel: SettingsViewModel = viewModel<SettingsViewModel>(factory = factory)

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
        { spellsScreen() },
        { charactersFiltersScreen() },
        { characterDetailScreen() },
        { quizzesScreen() },
        { quizDetailScreen() },
        { takeQuizScreen() },
        { quizResultScreen() },
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
