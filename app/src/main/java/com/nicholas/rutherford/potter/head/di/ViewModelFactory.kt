package com.nicholas.rutherford.potter.head.di

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import co.touchlab.kermit.Logger
import com.nicholas.rutherford.potter.head.feature.characters.characterdetail.CharacterDetailViewModel
import com.nicholas.rutherford.potter.head.feature.characters.characterdetail.CharacterDetailViewModelFactory
import com.nicholas.rutherford.potter.head.feature.characters.characters.CharactersViewModel
import com.nicholas.rutherford.potter.head.feature.quizzes.QuizzesViewModel
import com.nicholas.rutherford.potter.head.feature.settings.SettingsViewModel
import com.nicholas.rutherford.potter.head.navigation.Navigator

/**
 * Factory for creating ViewModels with Metro dependency injection.
 *
 * This factory uses AppGraph from Application to provide dependencies.
 * Following Metro best practices, dependencies are injected via constructor injection.
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
     * The `@Suppress("UNCHECKED_CAST")` annotation is required because we're casting from a
     * specific ViewModel type to the generic type parameter `T`. This is safe because we verify
     * the class type before creating the instance.
     *
     * @param modelClass The [Class] of the ViewModel to create.
     * @param extras Optional [CreationExtras] that may contain SavedStateHandle from navigation arguments.
     * @return A new instance of the requested ViewModel type with dependencies injected.
     * @throws IllegalArgumentException if the requested ViewModel class is not supported.
     */
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T =
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
     * This method retrieves the [HarryPotterApiRepository] and [Navigator] from the [AppGraph]
     * and passes them to the ViewModel constructor. All dependencies are provided via constructor injection.
     *
     * @return A new [CharactersViewModel] instance with the repository and navigator dependencies injected.
     */
    private fun createCharacterViewModel(): CharactersViewModel =
        CharactersViewModel(
            repository = appGraph.networkModule.harryPotterApiRepository,
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
    private fun createCharacterDetailViewModel(extras: CreationExtras): CharacterDetailViewModel {
        // Extract SavedStateHandle from CreationExtras (contains navigation arguments from Compose Navigation)
        // The viewModel() composable in Compose Navigation automatically provides SavedStateHandle in CreationExtras
        // We use the createSavedStateHandle() extension function which is the recommended way to extract it
        val savedStateHandle = extras.createSavedStateHandle()
        
        // Debug: Log all keys in SavedStateHandle to see what's available
        val keys = savedStateHandle.keys()
        log.d("SavedStateHandle keys: ${keys.joinToString()}")
        println("DEBUG Factory: SavedStateHandle keys: ${keys.joinToString()}")
        
        // Log the ID value for debugging
        val idValue = savedStateHandle.get<String>(com.nicholas.rutherford.potter.head.core.Constants.NamedArguments.ID)
        log.d("ID value in SavedStateHandle: $idValue")
        println("DEBUG Factory: ID value in SavedStateHandle: $idValue")
        
        // Use the factory with assisted injection pattern - SavedStateHandle is provided at creation time
        return appGraph.characterDetailViewModelFactory.create(savedStateHandle = savedStateHandle)
    }

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
        log.e("Unable to create ViewModel for class: ${modelClass.name}")
        throw IllegalArgumentException(
            "Unknown ViewModel class: ${modelClass.name}. " +
                "Add it to ViewModelFactory.create() to support injection."
        )
    }
}
