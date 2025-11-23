package com.nicholas.rutherford.potter.head.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import co.touchlab.kermit.Logger
import com.nicholas.rutherford.potter.head.network.HarryPotterApiRepository
import com.nicholas.rutherford.potter.head.entry.point.MainActivityViewModel
import com.nicholas.rutherford.potter.head.feature.characters.CharactersViewModel
import com.nicholas.rutherford.potter.head.feature.quizzes.QuizzesViewModel
import com.nicholas.rutherford.potter.head.feature.settings.SettingsViewModel

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
     * The `@Suppress("UNCHECKED_CAST")` annotation is required because we're casting from a
     * specific ViewModel type to the generic type parameter `T`. This is safe because we verify
     * the class type before creating the instance.
     *
     * @param modelClass The [Class] of the ViewModel to create.
     * @return A new instance of the requested ViewModel type with dependencies injected.
     * @throws IllegalArgumentException if the requested ViewModel class is not supported.
     */
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        when (modelClass) {
            MainActivityViewModel::class.java -> createMainActivityViewModel() as T
            CharactersViewModel::class.java -> createCharacterViewModel() as T
            QuizzesViewModel::class.java -> createQuizzesViewModel() as T
            SettingsViewModel::class.java -> createSettingsViewModel() as T
            else -> handleUnknownViewModel(modelClass)
        }

    /**
     * Creates a [MainActivityViewModel] instance with its dependencies injected.
     *
     * This method retrieves the [HarryPotterApiRepository] from the [AppGraph] and passes it
     * to the ViewModel constructor. All dependencies are provided via constructor injection.
     *
     * @return A new [MainActivityViewModel] instance with the repository dependency injected.
     */
    private fun createMainActivityViewModel(): MainActivityViewModel =
        MainActivityViewModel(repository = appGraph.networkModule.harryPotterApiRepository)

    /**
     * Creates a [CharactersViewModel] instance with its dependencies injected.
     *
     * This method retrieves the [HarryPotterApiRepository] from the [AppGraph] and passes it
     * to the ViewModel constructor. All dependencies are provided via constructor injection.
     *
     * @return A new [CharactersViewModel] instance with the repository dependency injected.
     */
    private fun createCharacterViewModel(): CharactersViewModel =
        CharactersViewModel(repository = appGraph.networkModule.harryPotterApiRepository)

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

        log.e("Unable to create ViewModel for class: ${modelClass.name}")
        throw IllegalArgumentException(errorMessage)
    }
}
