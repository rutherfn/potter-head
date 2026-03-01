package com.nicholas.rutherford.potter.head.network

import retrofit2.http.GET
import retrofit2.http.Query

/**
 * API service for searching character images.
 * Uses Wikipedia API to find Harry Potter character images.
 *
 * @author Nicholas Rutherford
 */
interface CharacterImageSearchService {

    /**
     * Searches for a character image using Wikipedia API.
     * 
     * @param characterName The name of the character to search for (e.g., "Harry Potter")
     * @return Wikipedia API response containing image information
     */
    @GET("api.php")
    suspend fun searchCharacterImage(
        @Query("action") action: String = "query",
        @Query("format") format: String = "json",
        @Query("prop") prop: String = "pageimages",
        @Query("pithumbsize") thumbSize: Int = 500,
        @Query("titles") characterName: String
    ): WikipediaImageResponse
}

/**
 * Response model for Wikipedia API image search.
 * Wikipedia API returns pages as a map where keys are page IDs (as strings).
 */
data class WikipediaImageResponse(
    val query: WikipediaQuery?
)

data class WikipediaQuery(
    val pages: Map<String, WikipediaPage>?
)

data class WikipediaPage(
    val pageid: Int?,
    val ns: Int?,
    val title: String?,
    val thumbnail: WikipediaThumbnail?,
    val original: WikipediaThumbnail? // Sometimes images are in 'original' field
)

data class WikipediaThumbnail(
    val source: String?,
    val width: Int?,
    val height: Int?
)

