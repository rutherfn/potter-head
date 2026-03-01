package com.nicholas.rutherford.potter.head.feature.characters.characters

import com.nicholas.rutherford.potter.head.base.view.model.BaseViewModel
import com.nicholas.rutherford.potter.head.base.view.model.FlowCollectionTrigger
import com.nicholas.rutherford.potter.head.core.Constants
import com.nicholas.rutherford.potter.head.core.StringIds
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
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * ViewModel for managing the characters list screen state and business logic.
 * Follows a cache-first strategy: loads from local database, fetches from API if empty.
 *
 * @param scope The coroutine scope for launching coroutines.
 * @param harryPotterApiRepository The repository for fetching characters from the Harry Potter API.
 * @param characterImageRepository The repository for managing character image URLs.
 * @param characterRepository The repository for managing characters in the local database.
 * @param debugToggleRepository The repository for managing debug toggle settings.
 * @param networkMonitor The network monitor for checking network connectivity.
 * @param navigator The navigator for navigating between screens.
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
    private val allCharacters = MutableStateFlow<List<CharacterConverter>>(value = emptyList())
    private var currentVisibleCount = Constants.INITIAL_PAGE_SIZE

    init {
        scope.launch { checkForCharacterImageUrlsForDb() }
        collectAllCharacters()
    }

    override fun getScope(): CoroutineScope = scope

    override fun getFlowCollectionTrigger(): FlowCollectionTrigger = FlowCollectionTrigger.INIT

    private fun collectAllCharacters() {
        charactersMutableStateFlow.update { state -> state.copy(isLoading = true) }
        
        collectFlow(flow = characterRepository.getAllCharacters()) { characters ->
            allCharacters.value = characters
            
            if (characters.isNotEmpty()) {
                updatePaginatedCharacters()
            } else {
                fetchCharactersFromApiAndUpdateDb()
            }
        }
    }

    private suspend fun checkForCharacterImageUrlsForDb() {
        val characterImageUrls = characterImageRepository.getAllCharacterImages().first()

        if (characterImageUrls.isEmpty()) {
            characterImageRepository.insertAllCharacterImageUrls()
        }
    }
    
    private fun updatePaginatedCharacters() {
        charactersMutableStateFlow.update { state ->
            state.copy(
                characters =  allCharacters.value.take(n = currentVisibleCount),
                errorType = CharactersErrorType.NONE,
                isLoading = false,
                hasMoreToLoad = allCharacters.value.size > currentVisibleCount
            )
        }
    }

    private fun failedFetchingCharacters(error: Throwable) {
        charactersMutableStateFlow.update { state ->
            state.copy(
                errorType = CharactersErrorType.FAILED_TO_FETCH_CHARACTERS,
                isLoading = false
            )
        }
        log.e("Failed to fetch characters from API with error message: ${error.message}")
    }


    private suspend fun fetchCharactersFromApiAndUpdateDb() {
        if (networkMonitor.isConnected()) {
            harryPotterApiRepository.getAllCharacters().first().onSuccess { characters ->
                val characterConverters = characters.map { characterResponse -> CharacterConverter.fromResponse(response = characterResponse) }
                characterRepository.insertAllCharacters(characters = characterConverters)
            }.onFailure { error -> failedFetchingCharacters(error = error) }
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

            delay(timeMillis = Constants.DELAY_LOADING_MORE_CHARACTERS )

            currentVisibleCount += charactersMutableStateFlow.value.pageSize
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
        scope.launch {
            val newCharacters = characterRepository.searchCharacters(query = query)

            allCharacters.value = newCharacters
            charactersMutableStateFlow.update { state -> state.copy(searchQuery = query, characters = newCharacters) }
        }
    }

    fun onClearClicked() {
        scope.launch {
            val newCharacters = characterRepository.searchCharacters(query = "")

            allCharacters.value = newCharacters
            charactersMutableStateFlow.update { state -> state.copy(searchQuery = "", characters = newCharacters) }
        }
    }

    fun onFilterClicked() {
        // TODO: Navigate to filter screen when implemented
        log.d("Filter button clicked")
    }

    fun onCharacterClicked(characterName: String) {
        // Navigate to character detail screen
        // Using character name as identifier since it's the primary key in the database
        val route = Constants.NavigationDestinations.CHARACTER_DETAIL_SCREEN_WITH_PARAMS
            .replace("{id}", characterName)
//        navigator.navigate(
//            SimpleNavigationAction(
//                destination = route
//            )
//        )
    }
}