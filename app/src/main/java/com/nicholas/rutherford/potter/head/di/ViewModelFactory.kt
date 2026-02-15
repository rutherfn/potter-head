package com.nicholas.rutherford.potter.head.di

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import co.touchlab.kermit.Logger
import com.nicholas.rutherford.potter.head.network.HarryPotterApiRepository
import com.nicholas.rutherford.potter.head.network.NetworkMonitor
import com.nicholas.rutherford.potter.head.feature.characters.characterdetail.CharacterDetailViewModel
import com.nicholas.rutherford.potter.head.feature.characters.characters.CharactersViewModel
import com.nicholas.rutherford.potter.head.feature.quizzes.QuizzesViewModel
import com.nicholas.rutherford.potter.head.feature.settings.SettingsViewModel
import com.nicholas.rutherford.potter.head.navigation.Navigator

/**
 * Factory for creating ViewModels with dependency injection.
 *
 * This factory uses AppGraph from Application to provide dependencies.
 * Dependencies are injected via constructor injection.
 *
 * @param appGraph The root dependency graph from Application, providing access to all modules.
 *
 * @author Nicholas Rutherford
 */
class ViewModelFactory(
    private val appGraph: AppGraph
) : ViewModelProvider.Factory {
    /**
     * Kermit Logger for this class.
     */
    private val log = Logger.withTag(tag = "ViewModelFactory")

    /**
     * Creates a ViewModel instance of the specified type with dependencies injected.
     *
     * This method uses a `when` expression to match the requested ViewModel class and delegates
     * to the appropriate creation method. Each ViewModel type has its own creation method that
     * handles dependency injection via the [AppGraph].
     *
     * When [CreationExtras] is provided (e.g., from Compose Navigation), it extracts the
     * [SavedStateHandle] which contains navigation arguments. Otherwise, it creates an empty
     * [SavedStateHandle] for ViewModels that require it.
     *
     * @param modelClass The [Class] of the ViewModel to create.
     * @param extras Optional [CreationExtras] that may contain SavedStateHandle from navigation arguments.
     *
     * @return A new instance of the requested ViewModel type with dependencies injected.
     * @throws IllegalArgumentException if the requested ViewModel class is not supported.
     */
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(
        modelClass: Class<T>,
        extras: CreationExtras
    ): T =
        when (modelClass) {
            CharactersViewModel::class.java -> createCharacterViewModel() as T
            CharacterDetailViewModel::class.java -> createCharacterDetailViewModel(extras) as T
            QuizzesViewModel::class.java -> createQuizzesViewModel() as T
            SettingsViewModel::class.java -> createSettingsViewModel() as T
            else -> handleUnknownViewModel(modelClass)
        }

    /**
     * Creates a [CharactersViewModel] instance with its dependencies injected.
     *
     * This method retrieves the [HarryPotterApiRepository], [NetworkMonitor], [Navigator], and [CoroutineScope]
     * from the [AppGraph] and passes them to the ViewModel constructor. All dependencies are provided via constructor injection.
     *
     * @return A new [CharactersViewModel] instance with the repository, network monitor, navigator, and scope dependencies injected.
     */
    private fun createCharacterViewModel(): CharactersViewModel =
        CharactersViewModel(
            scope = appGraph.scopeModule.viewModelScope,
            harryPotterApiRepository = appGraph.networkModule.harryPotterApiRepository,
            characterRepository = appGraph.databaseModule.characterRepository,
            debugToggleRepository = appGraph.databaseModule.debugToggleRepository,
            networkMonitor = appGraph.networkModule.networkMonitor,
            navigator = appGraph.navigatorModule.navigator
        )

    /**
     * Creates a [CharacterDetailViewModel] instance with its dependencies injected.
     *
     * This method extracts the [SavedStateHandle] from [CreationExtras] if available
     * (which contains navigation arguments from Compose Navigation), or creates an empty one if not.
     * This ensures that navigation arguments are properly passed to the ViewModel.
     *
     * @param extras [CreationExtras] that may contain SavedStateHandle from navigation arguments.
     * @return A new [CharacterDetailViewModel] instance with the repository, navigator, and SavedStateHandle injected.
     */
    private fun createCharacterDetailViewModel(extras: CreationExtras): CharacterDetailViewModel =
        appGraph.characterDetailViewModelFactory.create(savedStateHandle = extras.createSavedStateHandle())

    /**
     * Creates a [QuizzesViewModel] instance with its dependencies injected.
     *
     * @return A new [QuizzesViewModel] instance.
     */
    private fun createQuizzesViewModel(): QuizzesViewModel = QuizzesViewModel()

    /**
     * Creates a [SettingsViewModel] instance with its dependencies injected.
     *
     * @return A new [SettingsViewModel] instance.
     */
    private fun createSettingsViewModel(): SettingsViewModel = SettingsViewModel()

    /**
     * Handles the case when an unknown or unsupported ViewModel class is requested.
     *
     * This method logs an error message and throws an [IllegalArgumentException] with a
     * descriptive error message. The error message instructs developers to add the ViewModel
     * to the factory's `create` method to support dependency injection.
     *
     * @param modelClass The [Class] of the unsupported ViewModel.
     * @return Nothing (this function always throws an exception).
     * @throws IllegalArgumentException with a message indicating the ViewModel class is not supported.
     */
    private fun <T : ViewModel> handleUnknownViewModel(modelClass: Class<T>): Nothing {
        val errorMessage = "Unknown ViewModel class: ${modelClass.name}. Add it to ViewModelFactory.create() to support injection."

        log.e(errorMessage)
        throw IllegalArgumentException(errorMessage)
    }
}
