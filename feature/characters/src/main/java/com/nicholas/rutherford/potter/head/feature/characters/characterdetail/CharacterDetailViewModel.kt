package com.nicholas.rutherford.potter.head.feature.characters.characterdetail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nicholas.rutherford.potter.head.core.Constants
import com.nicholas.rutherford.potter.head.network.HarryPotterApiRepository
import com.nicholas.rutherford.potter.head.navigation.Navigator
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CharacterDetailViewModel(
    savedStateHandle: SavedStateHandle,
    private val repository: HarryPotterApiRepository,
    private val navigator: Navigator
) : ViewModel() {

    private var characterDetailMutableStateFlow = MutableStateFlow(value = CharacterDetailState())
    val characterDetailStateFlow = characterDetailMutableStateFlow.asStateFlow()

    internal val idParam: String = savedStateHandle.get<String>(Constants.NamedArguments.ID) ?: ""

    init {
        loadCharacterDetail()
    }

    private fun loadCharacterDetail() {
        if (idParam.isNotEmpty()) {
            println("full id param $idParam")
            viewModelScope.launch {
                repository.getCharacterById(id = idParam).collect { result ->
                    result.onSuccess {
                        println("here is the full data $it")
                    }.onFailure { error ->
                        println("here is the full error ${error.message}")
                    }
                }
            }
        } else {
            println("it is empty here")
            /// show some sort of error
        }
    }

    fun onBackClicked() {
        // Example: Pop back to previous screen
        navigator.pop(routeAction = null) // null means just pop back
    }
}