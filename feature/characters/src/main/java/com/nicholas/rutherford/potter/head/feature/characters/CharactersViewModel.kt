package com.nicholas.rutherford.potter.head.feature.characters

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nicholas.rutherford.potter.head.network.HarryPotterApiRepository
import dev.zacsweers.metro.Inject
import kotlinx.coroutines.launch

class CharactersViewModel @Inject constructor(
    private val repository: HarryPotterApiRepository
) : ViewModel() {

    init {
        loadAllCharacters()
    }

    private fun loadAllCharacters() {
        //todo -> More work on this its just placeholder for now
        viewModelScope.launch {
            repository.getAllCharacters().collect { result ->
                result.onSuccess { characters ->

                }.onFailure { error ->

                }
            }
        }
    }

    fun onCharacterClicked() {

    }
}