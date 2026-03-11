package com.nicholas.rutherford.potter.head.feature.spells

import com.nicholas.rutherford.potter.head.base.view.model.BaseViewModel
import com.nicholas.rutherford.potter.head.base.view.model.FlowCollectionTrigger
import com.nicholas.rutherford.potter.head.core.Constants
import com.nicholas.rutherford.potter.head.core.DataErrorType
import com.nicholas.rutherford.potter.head.database.converter.SpellConverter
import com.nicholas.rutherford.potter.head.database.repository.SpellRepository
import com.nicholas.rutherford.potter.head.network.HarryPotterApiRepository
import com.nicholas.rutherford.potter.head.network.NetworkMonitor
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update

/**
 * ViewModel for managing the spells list screen state and business logic.
 * Follows a cache-first strategy: loads from local database, fetches from API if empty.
 *
 * @param harryPotterApiRepository The repository for fetching characters from the Harry Potter API.
 * @param spellRepository The repository for managing spells in the local database.
 * @param networkMonitor The network monitor for checking network connectivity.
 *
 * @author Nicholas Rutherford
 */
class SpellsViewModel(
    private val harryPotterApiRepository: HarryPotterApiRepository,
    private val spellRepository: SpellRepository,
    private val networkMonitor: NetworkMonitor
) : BaseViewModel() {

    override val screenTitle: String = Constants.ScreenTitles.SPELLS

    private val spellsMutableStateFlow = MutableStateFlow(SpellsState())
    val spellsStateFlow: StateFlow<SpellsState> = spellsMutableStateFlow.asStateFlow()

    private var allSpells: List<SpellConverter> = emptyList()

    init {
        launch { collectAllSpells() }
    }

    override fun getFlowCollectionTrigger(): FlowCollectionTrigger = FlowCollectionTrigger.INIT

    private suspend fun collectAllSpells() {
        val hasSpellsInDb = spellRepository.getSpellCount() > 0

        if (!hasSpellsInDb) {
            spellsMutableStateFlow.update { state -> state.copy(isLoading = true) }
        }

        collectFlow(flow = spellRepository.getAllSpells()) { allSpellsFromDb ->
            allSpells = allSpellsFromDb
            
            if (spellsMutableStateFlow.value.searchQuery.isEmpty()) {
                
                if (allSpellsFromDb.isNotEmpty()) {
                    spellsMutableStateFlow.update { state ->
                        state.copy(
                            spells = allSpellsFromDb,
                            isLoading = false,
                            errorType = DataErrorType.None
                        )
                    }
                } else {
                    val currentErrorType = spellsMutableStateFlow.value.errorType
                    if (currentErrorType is DataErrorType.None) {
                        fetchSpellsFromApiAndUpdateDb()
                    } else {
                        spellsMutableStateFlow.update { state ->
                            state.copy(
                                spells = emptyList(),
                                isLoading = false
                            )
                        }
                    }
                }
            }
        }
    }

    private suspend fun fetchSpellsFromApiAndUpdateDb(): Boolean {
        return if (networkMonitor.isConnected()) {
            val result = harryPotterApiRepository.getAllSpells().first()
            result.getOrNull()?.let { spells ->
                val spellConverters = spells.map { spellResponse -> SpellConverter.fromResponse(response = spellResponse) }
                spellRepository.insertAllSpells(spells = spellConverters)
                true
            } ?: run {
                result.exceptionOrNull()?.let { error ->
                    failedFetchingSpells(error = error)
                } ?: run {
                    spellsMutableStateFlow.update { state ->
                        state.copy(
                            errorType = DataErrorType.FailedToFetchData(dataType = screenTitle),
                            isLoading = false
                        )
                    }
                    log.e("Failed to fetch spells: result was null without exception")
                }
                false
            }
        } else {
            spellsMutableStateFlow.update { state ->
                state.copy(
                    errorType = DataErrorType.NoInternetConnection,
                    isLoading = false
                )
            }
            false
        }
    }

    private fun failedFetchingSpells(error: Throwable) {
        spellsMutableStateFlow.update { state ->
            state.copy(
                errorType = DataErrorType.FailedToFetchData(dataType = screenTitle),
                isLoading = false
            )
        }
        log.e("Failed to fetch spells from API with error message: ${error.message}")
    }

    fun onSearchQueryChange(query: String) {
        launch {
            val newSpells = spellRepository.searchSpells(query = query)

            spellsMutableStateFlow.update { state -> state.copy(spells = newSpells, searchQuery = query) }
        }
    }

    fun onClearClicked() {
        launch {
            spellsMutableStateFlow.update { state -> 
                state.copy(
                    searchQuery = "",
                    spells = allSpells
                )
            }
        }
    }

    fun retryLoadingSpells() {
        spellsMutableStateFlow.update { state ->
            state.copy(
                errorType = DataErrorType.None,
                isLoading = true
            )
        }
        launch { fetchSpellsFromApiAndUpdateDb() }
    }
}