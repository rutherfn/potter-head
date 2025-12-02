package com.nicholas.rutherford.potter.head.feature.characters.characterdetail

import androidx.lifecycle.SavedStateHandle
import com.nicholas.rutherford.potter.head.navigation.Navigator
import com.nicholas.rutherford.potter.head.network.HarryPotterApiRepository

/**
 * Factory for creating [CharacterDetailViewModel] instances.
 *
 * This factory uses dependency injection to provide the repository and navigator,
 * while accepting [SavedStateHandle] as an assisted parameter (provided at creation time).
 * This pattern is similar to AssistedInject, allowing us to inject dependencies via DI
 * while providing runtime parameters like SavedStateHandle.
 *
 * @param repository The [HarryPotterApiRepository] to inject into the ViewModel.
 * @param navigator The [Navigator] to inject into the ViewModel.
 *
 * @author Nicholas Rutherford
 */
class CharacterDetailViewModelFactory(
    private val repository: HarryPotterApiRepository,
    private val navigator: Navigator
) {
    /**
     * Creates a [CharacterDetailViewModel] instance with the provided [SavedStateHandle].
     *
     * The repository and navigator are injected via the factory constructor, while
     * SavedStateHandle is provided at creation time (typically from navigation arguments).
     *
     * @param savedStateHandle The [SavedStateHandle] containing navigation arguments or saved state.
     * @return A new [CharacterDetailViewModel] instance with all dependencies injected.
     */
    fun create(savedStateHandle: SavedStateHandle): CharacterDetailViewModel {
        return CharacterDetailViewModel(
            savedStateHandle = savedStateHandle,
            repository = repository,
            navigator = navigator
        )
    }
}

