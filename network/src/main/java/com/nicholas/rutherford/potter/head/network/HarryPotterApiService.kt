package com.nicholas.rutherford.potter.head.network

import com.nicholas.rutherford.potter.head.model.network.CharacterResponse
import retrofit2.http.GET
import retrofit2.http.Path

/**
 *
 * API service for the [Harry Potter API](https://hp-api.onrender.com/).
 *
 * @author Nicholas Rutherford
 */
interface HarryPotterApiService {
    /**
     * Fetches all characters from the API.
     *
     * @return A list of [CharacterResponse] objects representing the characters.
     */
    @GET("characters")
    suspend fun fetchAllCharacters(): List<CharacterResponse>
}