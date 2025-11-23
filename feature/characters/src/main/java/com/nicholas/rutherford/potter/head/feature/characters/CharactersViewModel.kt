package com.nicholas.rutherford.potter.head.feature.characters

import androidx.lifecycle.ViewModel
import com.nicholas.rutherford.potter.head.network.HarryPotterApiRepository
import dev.zacsweers.metro.Inject

class CharactersViewModel @Inject constructor(
    private val repository: HarryPotterApiRepository
) : ViewModel() {

    fun onCharacterClicked() {

    }
}