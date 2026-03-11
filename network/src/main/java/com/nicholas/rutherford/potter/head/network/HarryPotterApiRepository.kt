package com.nicholas.rutherford.potter.head.network

import com.nicholas.rutherford.potter.head.model.network.CharacterResponse
import com.nicholas.rutherford.potter.head.model.network.SpellResponse
import kotlinx.coroutines.flow.Flow

/**
 * Repository interface for fetching characters from the Harry Potter API,uses [HarryPotterApiService]
 *
 * @author Nicholas Rutherford
 */
interface HarryPotterApiRepository {
    fun getAllCharacters(): Flow<Result<List<CharacterResponse>>>
    fun getAllSpells(): Flow<Result<List<SpellResponse>>>
}