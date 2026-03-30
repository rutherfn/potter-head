package com.nicholas.rutherford.potter.head.di

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import co.touchlab.kermit.Logger
import com.nicholas.rutherford.potter.head.feature.characters.characterdetail.CharacterDetailViewModel
import com.nicholas.rutherford.potter.head.feature.characters.characterfilters.CharacterFiltersViewModel
import com.nicholas.rutherford.potter.head.feature.characters.characters.CharactersViewModel
import com.nicholas.rutherford.potter.head.feature.quizzes.QuizzesViewModel
import com.nicholas.rutherford.potter.head.feature.quizzes.quizdetail.QuizDetailViewModel
import com.nicholas.rutherford.potter.head.feature.quizzes.quizresult.QuizResultViewModel
import com.nicholas.rutherford.potter.head.feature.quizzes.takequiz.TakeQuizViewModel
import com.nicholas.rutherford.potter.head.feature.settings.SettingsViewModel
import com.nicholas.rutherford.potter.head.feature.spells.SpellsViewModel

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
    private val appGraph: AppGraph,
    private val application: Application
) : ViewModelProvider.Factory {
    private val log = Logger.withTag(tag = "ViewModelFactory")

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(
        modelClass: Class<T>,
        extras: CreationExtras
    ): T =
        when (modelClass) {
            CharactersViewModel::class.java -> createCharacterViewModel() as T
            CharacterFiltersViewModel::class.java -> createCharacterFiltersViewModel() as T
            SpellsViewModel::class.java -> createSpellsViewModel() as T
            CharacterDetailViewModel::class.java -> createCharacterDetailViewModel(extras = extras) as T
            QuizzesViewModel::class.java -> createQuizzesViewModel() as T
            QuizDetailViewModel::class.java -> createQuizDetailViewModel(extras = extras) as T
            QuizResultViewModel::class.java -> createQuizResultViewModel(extras = extras) as T
            TakeQuizViewModel::class.java -> createTakeQuizViewModel(extras = extras) as T
            SettingsViewModel::class.java -> createSettingsViewModel() as T
            else -> handleUnknownViewModel(modelClass)
        }

    private fun createCharacterViewModel(): CharactersViewModel =
        CharactersViewModel(
            harryPotterApiRepository = appGraph.networkModule.harryPotterApiRepository,
            characterImageRepository = appGraph.databaseModule.characterImageRepository,
            characterRepository = appGraph.databaseModule.characterRepository,
            characterFilterRepository = appGraph.databaseModule.characterFilterRepository,
            networkMonitor = appGraph.networkModule.networkMonitor,
            navigator = appGraph.navigatorModule.navigator
        )

    private fun createCharacterFiltersViewModel(): CharacterFiltersViewModel =
        CharacterFiltersViewModel(
            characterFilterRepository = appGraph.databaseModule.characterFilterRepository,
            navigator = appGraph.navigatorModule.navigator
        )

    private fun createSpellsViewModel(): SpellsViewModel =
        SpellsViewModel(
            harryPotterApiRepository = appGraph.networkModule.harryPotterApiRepository,
            spellRepository = appGraph.databaseModule.spellRepository,
            networkMonitor = appGraph.networkModule.networkMonitor
        )

    private fun createCharacterDetailViewModel(extras: CreationExtras): CharacterDetailViewModel =
        CharacterDetailViewModel(
            savedStateHandle = extras.createSavedStateHandle(),
            characterDao = appGraph.databaseModule.characterDao,
            navigator = appGraph.navigatorModule.navigator,
            application = application
        )

    private fun createQuizzesViewModel(): QuizzesViewModel = QuizzesViewModel(
        application = application,
        navigator = appGraph.navigatorModule.navigator,
        quizRepository = appGraph.databaseModule.quizRepository
    )

    private fun createQuizDetailViewModel(extras: CreationExtras): QuizDetailViewModel = QuizDetailViewModel(
        savedStateHandle = extras.createSavedStateHandle(),
        navigator = appGraph.navigatorModule.navigator
    )

    private fun createQuizResultViewModel(extras: CreationExtras): QuizResultViewModel = QuizResultViewModel(
        savedStateHandle = extras.createSavedStateHandle(),
        savedQuizRepository = appGraph.databaseModule.savedQuizRepository,
        navigator = appGraph.navigatorModule.navigator
    )

    private fun createTakeQuizViewModel(extras: CreationExtras): TakeQuizViewModel = TakeQuizViewModel(
        savedStateHandle = extras.createSavedStateHandle(),
        application = application,
        navigator = appGraph.navigatorModule.navigator,
        quizRepository = appGraph.databaseModule.quizRepository,
        savedQuizRepository = appGraph.databaseModule.savedQuizRepository
    )

    private fun createSettingsViewModel(): SettingsViewModel = SettingsViewModel()

    private fun <T : ViewModel> handleUnknownViewModel(modelClass: Class<T>): Nothing {
        val errorMessage = "Unknown ViewModel class: ${modelClass.name}. Add it to ViewModelFactory.create() to support injection."

        log.e(errorMessage)
        throw IllegalArgumentException(errorMessage)
    }
}
