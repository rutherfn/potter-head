package com.nicholas.rutherford.potter.head.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import co.touchlab.kermit.Logger
import com.nicholas.rutherford.potter.head.feature.characters.characterdetail.CharacterDetailViewModel
import com.nicholas.rutherford.potter.head.feature.characters.characters.CharactersViewModel
import com.nicholas.rutherford.potter.head.feature.quizzes.QuizzesViewModel
import com.nicholas.rutherford.potter.head.feature.settings.SettingsViewModel

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

    private fun createCharacterViewModel(): CharactersViewModel =
        CharactersViewModel(
            harryPotterApiRepository = appGraph.networkModule.harryPotterApiRepository,
            characterImageRepository = appGraph.databaseModule.characterImageRepository,
            characterRepository = appGraph.databaseModule.characterRepository,
            networkMonitor = appGraph.networkModule.networkMonitor,
            navigator = appGraph.navigatorModule.navigator
        )

    private fun createCharacterDetailViewModel(extras: CreationExtras): CharacterDetailViewModel =
        appGraph.characterDetailViewModelFactory.create(savedStateHandle = extras.createSavedStateHandle())

    private fun createQuizzesViewModel(): QuizzesViewModel = QuizzesViewModel()

    private fun createSettingsViewModel(): SettingsViewModel = SettingsViewModel()

    private fun <T : ViewModel> handleUnknownViewModel(modelClass: Class<T>): Nothing {
        val errorMessage = "Unknown ViewModel class: ${modelClass.name}. Add it to ViewModelFactory.create() to support injection."

        log.e(errorMessage)
        throw IllegalArgumentException(errorMessage)
    }
}
