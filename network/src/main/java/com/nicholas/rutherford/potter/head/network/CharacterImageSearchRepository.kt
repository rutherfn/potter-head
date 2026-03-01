package com.nicholas.rutherford.potter.head.network

import co.touchlab.kermit.Logger

/**
 * Repository interface for searching character images.
 *
 * @author Nicholas Rutherford
 */
interface CharacterImageSearchRepository {

    /**
     * Searches for an image URL for a given character name.
     * 
     * @param characterName The name of the character to search for
     * @return The image URL if found, null otherwise
     */
    suspend fun searchCharacterImage(characterName: String): String?
}

/**
 * Implementation of CharacterImageSearchRepository using Wikipedia API.
 */
class CharacterImageSearchRepositoryImpl(
    private val imageSearchService: CharacterImageSearchService,
    private val networkMonitor: NetworkMonitor
) : CharacterImageSearchRepository {

    private val log = Logger.withTag(tag = "CharacterImageSearchRepository")

    override suspend fun searchCharacterImage(characterName: String): String? {
        return try {
            if (!networkMonitor.isConnected()) {
                log.w("No network connection, cannot search for image: $characterName")
                return null
            }

            // Try multiple search strategies for better results
            val searchQueries = listOf(
                "$characterName (Harry Potter)", // Most specific
                characterName, // Direct character name
                "$characterName character" // With character suffix
            )
            
            var imageUrl: String? = null
            
            // Try each search query until we find an image
            for (searchQuery in searchQueries) {
                try {
                    val response = imageSearchService.searchCharacterImage(characterName = searchQuery)
                    
                    // Extract image URL from response
                    // Wikipedia API returns pages as a map, we need to get the first page
                    val page = response.query?.pages?.values?.firstOrNull()
                    imageUrl = page?.thumbnail?.source 
                        ?: page?.original?.source // Fallback to original if thumbnail not available
                    
                    if (imageUrl != null) {
                        log.d("Found image for $characterName using query '$searchQuery': $imageUrl")
                        break // Found an image, stop searching
                    }
                } catch (e: Exception) {
                    log.w("Search query '$searchQuery' failed for $characterName: ${e.message}")
                    // Continue to next query
                }
            }

            if (imageUrl == null) {
                log.w("No image found for character: $characterName after trying all search queries")
            }

            imageUrl
        } catch (e: Exception) {
            log.e("Failed to search for image: $characterName", e)
            null
        }
    }
}

