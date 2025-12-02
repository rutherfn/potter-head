package com.nicholas.rutherford.potter.head.feature.characters.characters

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nicholas.rutherford.potter.head.core.Constants
import com.nicholas.rutherford.potter.head.navigation.Navigator
import com.nicholas.rutherford.potter.head.navigation.SimpleNavigationAction
import com.nicholas.rutherford.potter.head.network.HarryPotterApiRepository
import kotlinx.coroutines.launch

class CharactersViewModel(
    private val repository: HarryPotterApiRepository,
    private val navigator: Navigator
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

    fun onCharacterClicked(characterId: String = "9e3f7ce4-b9a7-4244-b709-dae5c1f1d4a8") {
        // Navigate to character detail screen
        // Replace {id} placeholder in the route with the actual character ID
        val route = Constants.NavigationDestinations.CHARACTER_DETAIL_SCREEN_WITH_PARAMS
            .replace("{id}", characterId)
        navigator.navigate(
            SimpleNavigationAction(
                destination = route
            )
        )
    }
}