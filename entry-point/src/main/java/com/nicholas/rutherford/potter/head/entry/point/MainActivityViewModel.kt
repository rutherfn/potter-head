package com.nicholas.rutherford.potter.head.entry.point

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nicholas.rutherford.potter.head.model.network.CharacterResponse
import com.nicholas.rutherford.potter.head.network.HarryPotterApiRepository
import dev.zacsweers.metro.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * ViewModel for MainActivity.
 * Manages character data and UI state using Metro dependency injection.
 */
class MainActivityViewModel @Inject constructor(
    private val repository: HarryPotterApiRepository
) : ViewModel() {
    
    private val _uiState = MutableStateFlow<CharactersUiState>(CharactersUiState.Loading)
    val uiState: StateFlow<CharactersUiState> = _uiState.asStateFlow()
    
    init {
        loadAllCharacters()
    }
    
    /**
     * Loads all characters from the repository.
     * Updates UI state based on the result.
     */
    private fun loadAllCharacters() {
        viewModelScope.launch {
            repository.getAllCharacters().collect { result ->
                result.onSuccess { characters ->
                    _uiState.value = CharactersUiState.Success(characters)
                    // Log each character for validation
                    characters.forEach { character ->
                    }
                }.onFailure { error ->
                    _uiState.value = CharactersUiState.Error(error.message ?: "Unknown error")
                }
            }
        }
    }
}

/**
 * UI state for the characters screen.
 */
sealed interface CharactersUiState {
    data object Loading : CharactersUiState
    data class Success(val characters: List<CharacterResponse>) : CharactersUiState
    data class Error(val message: String) : CharactersUiState
}

