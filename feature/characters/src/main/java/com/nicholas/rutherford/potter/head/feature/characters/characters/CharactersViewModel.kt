package com.nicholas.rutherford.potter.head.feature.characters.characters

import android.net.Uri
import com.nicholas.rutherford.potter.head.base.view.model.BaseViewModel
import com.nicholas.rutherford.potter.head.base.view.model.FlowCollectionTrigger
import com.nicholas.rutherford.potter.head.core.Constants
import com.nicholas.rutherford.potter.head.core.DataErrorType
import com.nicholas.rutherford.potter.head.core.StringIds
import com.nicholas.rutherford.potter.head.database.converter.CharacterConverter
import com.nicholas.rutherford.potter.head.database.repository.CharacterFilterRepository
import com.nicholas.rutherford.potter.head.database.repository.CharacterImageRepository
import com.nicholas.rutherford.potter.head.database.repository.CharacterRepository
import com.nicholas.rutherford.potter.head.database.repository.getActiveFilterCount
import com.nicholas.rutherford.potter.head.navigation.Navigator
import com.nicholas.rutherford.potter.head.navigation.SimpleNavigationAction
import com.nicholas.rutherford.potter.head.network.HarryPotterApiRepository
import com.nicholas.rutherford.potter.head.network.NetworkMonitor
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import java.util.concurrent.atomic.AtomicInteger

/**
 * ViewModel for managing the characters list screen state and business logic.
 * Follows a cache-first strategy: loads from local database, fetches from API if empty.
 *
 * @param harryPotterApiRepository The repository for fetching characters from the Harry Potter API.
 * @param characterImageRepository The repository for managing character image URLs.
 * @param characterRepository The repository for managing characters in the local database.
 * @param characterFilterRepository The repository for managing character filters.
 * @param networkMonitor The network monitor for checking network connectivity.
 * @param navigator The navigator for navigating between screens.
 *
 * @author Nicholas Rutherford
 */
class CharactersViewModel(
    private val harryPotterApiRepository: HarryPotterApiRepository,
    private val characterImageRepository: CharacterImageRepository,
    private val characterRepository: CharacterRepository,
    private val characterFilterRepository: CharacterFilterRepository,
    private val networkMonitor: NetworkMonitor,
    private val navigator: Navigator
) : BaseViewModel() {

    override val screenTitle: String = Constants.ScreenTitles.CHARACTERS

    private val charactersMutableStateFlow = MutableStateFlow(CharactersState())
    val charactersStateFlow: StateFlow<CharactersState> = charactersMutableStateFlow.asStateFlow()
    private val allCharacters = MutableStateFlow<List<CharacterConverter>>(value = emptyList())
    private val currentVisibleCount = AtomicInteger(Constants.INITIAL_PAGE_SIZE)

    init {
        launch { checkForCharacterImageUrlsForDb() }
        launch { collectAllCharacters() }
        collectFilterCount()
    }

    override fun getFlowCollectionTrigger(): FlowCollectionTrigger = FlowCollectionTrigger.INIT

    private suspend fun collectAllCharacters() {
        val hasCharactersInDb = characterRepository.getCharacterCount() > 0
        if (!hasCharactersInDb) {
            charactersMutableStateFlow.update { state -> state.copy(isLoading = true) }
        }
        
        collectFlow(flow = characterRepository.getAllCharacters()) { characters ->
            if (charactersMutableStateFlow.value.searchQuery.isNotEmpty()) {
                allCharacters.value = characters

                if (characters.isNotEmpty() && charactersMutableStateFlow.value.isLoading) {
                    charactersMutableStateFlow.update { state ->
                        state.copy(
                            isLoading = false,
                            shouldShowNoContent = false,
                            errorType = DataErrorType.None
                        )
                    }
                } else if (characters.isEmpty() && charactersMutableStateFlow.value.isLoading) {
                    charactersMutableStateFlow.update { state ->
                        state.copy(
                            isLoading = false,
                            shouldShowNoContent = false,
                            errorType = DataErrorType.None
                        )
                    }
                }
            } else {
                allCharacters.value = characters
                
                if (characters.isNotEmpty()) {
                    updatePaginatedCharacters()
                } else {
                    val currentFilterCount = charactersMutableStateFlow.value.filterCount
                    if (currentFilterCount > 0) {
                        charactersMutableStateFlow.update { state ->
                            state.copy(
                                characters = emptyList(),
                                errorType = DataErrorType.None,
                                isLoading = false,
                                shouldShowNoContent = false,
                                hasMoreToLoad = false
                            )
                        }
                    } else {
                        fetchCharactersFromApiAndUpdateDb()
                    }
                }
            }
        }
    }

    private suspend fun checkForCharacterImageUrlsForDb() {
        val characterImageUrls = characterImageRepository.getAllCharacterImages().first()

        if (characterImageUrls.isEmpty()) {
            characterImageRepository.insertAllCharacterImageUrls()
        }
    }

    private fun collectFilterCount() {
        collectFlow(flow = characterFilterRepository.getCharacterFilters()) {
            val filterCount = characterFilterRepository.getActiveFilterCount()
            charactersMutableStateFlow.update { state ->
                state.copy(filterCount = filterCount)
            }
        }
    }
    
    private fun updatePaginatedCharacters() {
        val visibleCount = currentVisibleCount.get()
        charactersMutableStateFlow.update { state ->
            state.copy(
                characters =  allCharacters.value.take(n = visibleCount),
                errorType = DataErrorType.None,
                isLoading = false,
                shouldShowNoContent = false,
                hasMoreToLoad = allCharacters.value.size > visibleCount
            )
        }
    }

    private fun failedFetchingCharacters(error: Throwable) {
        charactersMutableStateFlow.update { state ->
            state.copy(
                errorType = DataErrorType.FailedToFetchData(dataType = screenTitle),
                isLoading = false,
                shouldShowNoContent = false,
            )
        }
        log.e("Failed to fetch characters from API with error message: ${error.message}")
    }


    private suspend fun fetchCharactersFromApiAndUpdateDb(): Boolean {
        return if (networkMonitor.isConnected()) {
            val result = harryPotterApiRepository.getAllCharacters().first()
            result.getOrNull()?.let { characters ->
                val characterConverters = characters.map { characterResponse -> CharacterConverter.fromResponse(response = characterResponse) }
                characterRepository.insertAllCharacters(characters = characterConverters)
                true
            } ?: run {
                result.exceptionOrNull()?.let { error -> 
                    failedFetchingCharacters(error = error)
                } ?: run {
                    charactersMutableStateFlow.update { state ->
                        state.copy(
                            errorType = DataErrorType.FailedToFetchData(dataType = screenTitle),
                            isLoading = false,
                            shouldShowNoContent = false,
                        )
                    }
                    log.e("Failed to fetch characters: result was null without exception")
                }
                false
            }
        } else {
            charactersMutableStateFlow.update { state ->
                state.copy(
                    errorType = DataErrorType.NoInternetConnection,
                    isLoading = false,
                    shouldShowNoContent = false,
                )
            }
            false
        }
    }

    fun retryLoadingCharacters() {
        launch {
            charactersMutableStateFlow.update { state -> 
                state.copy(
                    isLoading = true,
                    shouldShowNoContent = false,
                    errorType = DataErrorType.None
                )
            }
            delay(timeMillis = Constants.RETRY_LOADING_CHARACTERS_DELAY)
            currentVisibleCount.set(charactersMutableStateFlow.value.pageSize)

            if (!fetchCharactersFromApiAndUpdateDb()) {
                charactersMutableStateFlow.update { state ->
                    if (state.isLoading) {
                        state.copy(isLoading = false, shouldShowNoContent = false)
                    } else {
                        state.copy(shouldShowNoContent = false)
                    }
                }
            }
        }
    }

    fun loadMoreCharacters() {
        if (currentVisibleCount.get() >=  allCharacters.value.size) {
            return
        }
        
        launch {
            charactersMutableStateFlow.update { state -> state.copy(isLoadingMore = true) }

            delay(timeMillis = Constants.DELAY_LOADING_MORE_CHARACTERS )

            currentVisibleCount.addAndGet(charactersMutableStateFlow.value.pageSize)
            updatePaginatedCharacters()
            
            charactersMutableStateFlow.update { state -> state.copy(isLoadingMore = false) }
        }
    }

    fun buildCharacterStatusIds(characterConverter: CharacterConverter): List<Int> {
        return buildList {
            if (characterConverter.isHogwartsStudent) {
                add(StringIds.student)
            }

            if (characterConverter.isHogwartsStaff) {
                add(StringIds.staff)
            }

            if (characterConverter.isWizard) {
                add(StringIds.wizard)
            }
        }
    }

    fun onSearchQueryChange(query: String) {
        launch {
            val newCharacters = characterRepository.searchCharacters(query = query)

            currentVisibleCount.set(charactersMutableStateFlow.value.pageSize)
            
            allCharacters.value = newCharacters
            charactersMutableStateFlow.update { state -> state.copy(searchQuery = query) }

            updatePaginatedCharacters()
        }
    }

    fun onClearClicked() {
        launch {
            charactersMutableStateFlow.update { state -> 
                state.copy(
                    searchQuery = "",
                    shouldShowNoContent = true
                )
            }

            currentVisibleCount.set(charactersMutableStateFlow.value.pageSize)

            val currentCharacters = characterRepository.getAllCharacters().first()
            allCharacters.value = currentCharacters

            updatePaginatedCharacters()
        }
    }

    fun onFilterClicked() = navigator.navigate(navigationAction = SimpleNavigationAction(destination = Constants.NavigationDestinations.CHARACTERS_FILTERS_SCREEN))

    fun onClearFiltersClicked() {
        launch {
            characterFilterRepository.resetFilters()

        }
    }

    fun onCharacterClicked(name: String) {
        val encodedCharacterName = Uri.encode(name)
        val route = Constants.NavigationDestinations.CHARACTER_DETAIL_SCREEN_WITH_PARAMS
            .replace("{name}", encodedCharacterName)
        navigator.navigate(
            SimpleNavigationAction(
                destination = route
            )
        )
    }
}
