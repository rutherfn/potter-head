package com.nicholas.rutherford.potter.head.feature.characters.characters

import com.nicholas.rutherford.potter.head.base.view.model.BaseViewModel
import com.nicholas.rutherford.potter.head.core.Constants
import com.nicholas.rutherford.potter.head.database.converter.CharacterConverter
import com.nicholas.rutherford.potter.head.database.repository.CharacterImageRepository
import com.nicholas.rutherford.potter.head.database.repository.CharacterRepository
import com.nicholas.rutherford.potter.head.database.repository.DebugToggleRepository
import com.nicholas.rutherford.potter.head.navigation.Navigator
import com.nicholas.rutherford.potter.head.navigation.SimpleNavigationAction
import com.nicholas.rutherford.potter.head.network.HarryPotterApiRepository
import com.nicholas.rutherford.potter.head.network.NetworkMonitor
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * ViewModel for managing the characters list screen state and business logic.
 * Follows a cache-first strategy: loads from local database, fetches from API if empty.
 *
 * @author Nicholas Rutherford
 */
class CharactersViewModel(
    private val scope: CoroutineScope,
    private val harryPotterApiRepository: HarryPotterApiRepository,
    private val characterImageRepository: CharacterImageRepository,
    private val characterRepository: CharacterRepository,
    private val debugToggleRepository: DebugToggleRepository,
    private val networkMonitor: NetworkMonitor,
    private val navigator: Navigator
) : BaseViewModel() {

    private val charactersMutableStateFlow = MutableStateFlow(CharactersState())
    val charactersStateFlow: StateFlow<CharactersState> = charactersMutableStateFlow.asStateFlow()
    
    private var hasInitiatedFetch = false
    
    // Track all characters from database (for pagination)
    private val allCharacters = MutableStateFlow<List<CharacterConverter>>(value = emptyList())
    private var currentVisibleCount = Constants.INITIAL_PAGE_SIZE

    init {
        scope.launch { loadAllCharacters() }
    }

    private suspend fun loadAllCharacters() {
        ensureCharacterImageUrlsInitialized()

        charactersMutableStateFlow.update { state -> state.copy(isLoading = true) }

        characterRepository.getAllCharacters().collectLatest { storedCharacters ->

            // Store all characters for pagination
            allCharacters.value = storedCharacters
            
            if (storedCharacters.isNotEmpty()) {
                updatePaginatedCharacters()
            } else {
                fetchCharactersFromApiAndUpdateDb()
            }
        }
    }

    private suspend fun ensureCharacterImageUrlsInitialized() {
        val characterImageUrls = characterImageRepository.getAllCharacterImages().first()
        if (characterImageUrls.isEmpty()) {
            characterImageRepository.insertAllCharacterImageUrls()
        }
    }
    
    private fun updatePaginatedCharacters() {
        val allChars = allCharacters.value
        val paginatedList = allChars.take(n = currentVisibleCount)
        val hasMore = allChars.size > currentVisibleCount
        
        charactersMutableStateFlow.update { state ->
            state.copy(
                characters = paginatedList,
                errorType = CharactersErrorType.NONE,
                isLoading = false,
                hasMoreToLoad = hasMore
            )
        }
    }


    private suspend fun fetchCharactersFromApiAndUpdateDb() {
        if (networkMonitor.isConnected()) {
            harryPotterApiRepository.getAllCharacters().first().onSuccess { characters ->
                val characterConverters = characters.map { characterResponse -> CharacterConverter.fromResponse(response = characterResponse) }
                characterRepository.insertAllCharacters(characters = characterConverters)
            }.onFailure { error ->
                charactersMutableStateFlow.update { state ->
                    state.copy(
                        errorType = CharactersErrorType.FAILED_TO_FETCH_CHARACTERS,
                        isLoading = false
                    )
                }
                log.e("Failed to fetch characters from API with error message: ${error.message}")
            }
        } else {
            charactersMutableStateFlow.update { state ->
                state.copy(
                    errorType = CharactersErrorType.NO_INTERNET_CONNECTION,
                    isLoading = false
                )
            }
        }
    }

    fun retryLoadingCharacters() {
        scope.launch {
            charactersMutableStateFlow.update { state -> state.copy(isLoading = true) }
            delay(timeMillis = Constants.RETRY_LOADING_CHARACTERS_DELAY)
            hasInitiatedFetch = false
            currentVisibleCount = charactersMutableStateFlow.value.pageSize
            fetchCharactersFromApiAndUpdateDb()
        }
    }

    fun loadMoreCharacters() {
        if (currentVisibleCount >=  allCharacters.value.size) {
            return
        }
        
        scope.launch {
            charactersMutableStateFlow.update { state -> state.copy(isLoadingMore = true) }

            delay(Constants.DELAY_LOADING_MORE_CHARACTERS )
            
            // Increase visible count by page size
            currentVisibleCount += charactersMutableStateFlow.value.pageSize
            updatePaginatedCharacters()
            
            charactersMutableStateFlow.update { state -> state.copy(isLoadingMore = false) }
        }
    }

    fun onCharacterClicked(characterName: String) {
        // Navigate to character detail screen
        // Using character name as identifier since it's the primary key in the database
        val route = Constants.NavigationDestinations.CHARACTER_DETAIL_SCREEN_WITH_PARAMS
            .replace("{id}", characterName)
        navigator.navigate(
            SimpleNavigationAction(
                destination = route
            )
        )
    }
}