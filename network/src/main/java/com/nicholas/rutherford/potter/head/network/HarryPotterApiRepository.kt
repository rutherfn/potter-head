package com.nicholas.rutherford.potter.head.network

import com.nicholas.rutherford.potter.head.model.network.CharacterResponse
import kotlinx.coroutines.flow.Flow

/**
 * Repository interface for fetching characters from the Harry Potter API,uses [HarryPotterApiService]
 *
 * @author Nicholas Rutherford
 */
interface HarryPotterApiRepository {

    /**
     * Fetches all characters from the API.
     *
     * @return A [Flow] emitting a [Result] containing a list of [CharacterResponse] objects.
     */
    fun getAllCharacters(): Flow<Result<List<CharacterResponse>>>

    /**
     * Fetches a character by the id from the API
     *
     * @param id The ID of the character to fetch.
     *
     * @return A [Flow] emitting a [Result] containing a [CharacterResponse] object.
     */
    fun getCharacterById(id: String): Flow<Result<CharacterResponse>>
}