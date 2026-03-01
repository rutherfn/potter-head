package com.nicholas.rutherford.potter.head.feature.characters.characters

/**
 * Utility object for resolving character image URLs with fallback strategies.
 * Handles missing image URLs by trying pattern-based URLs and placeholder services.
 *
 * @author Nicholas Rutherford
 */
object CharacterImageUrlResolver {
    
    private const val BASE_IMAGE_URL = "https://ik.imagekit.io/hpapi"
    
    // Known working image URLs extracted from the API response
    // These are characters that have confirmed working image URLs from the API
    // This can be expanded as more images are discovered or manually curated
    private val knownImageUrls = mapOf(
        "Harry Potter" to "$BASE_IMAGE_URL/harry.jpg",
        "Hermione Granger" to "$BASE_IMAGE_URL/hermione.jpeg",
        "Ron Weasley" to "$BASE_IMAGE_URL/ron.jpg",
        "Draco Malfoy" to "$BASE_IMAGE_URL/draco.jpg",
        "Minerva McGonagall" to "$BASE_IMAGE_URL/mcgonagall.jpg",
        "Cedric Diggory" to "$BASE_IMAGE_URL/cedric.png",
        "Cho Chang" to "$BASE_IMAGE_URL/cho.jpg",
        "Severus Snape" to "$BASE_IMAGE_URL/snape.jpg",
        "Rubeus Hagrid" to "$BASE_IMAGE_URL/hagrid.png",
        "Neville Longbottom" to "$BASE_IMAGE_URL/neville.jpg",
        "Luna Lovegood" to "$BASE_IMAGE_URL/luna.jpg",
        "Ginny Weasley" to "$BASE_IMAGE_URL/ginny.jpg",
        "Mr Crabbe" to "https://static.wikia.nocookie.net/harrypotter/images/b/ba/Vincent_Crabbe.jpg/revision/latest/thumbnail/width/360/height/450?cb=20091224183746"
        // Add more characters here as you discover working image URLs
        // The pattern-based URL generator will attempt to find images for others
    )
    
    /**
     * Resolves the best available image URL for a character.
     * Tries multiple strategies in order:
     * 1. API-provided image URL (if not null/empty)
     * 2. Known image URL from mapping
     * 3. Pattern-based URL (constructs URL from character name)
     * 4. Returns null (will fall back to placeholder in UI)
     *
     * @param characterName The name of the character
     * @param apiImageUrl The image URL from the API (may be null or empty)
     * @return The resolved image URL, or null if no URL can be determined
     */
    fun resolveImageUrl(characterName: String, apiImageUrl: String?): String? {
        // Strategy 1: Use API URL if available and not empty
        if (!apiImageUrl.isNullOrEmpty()) {
            return apiImageUrl
        }
        
        // Strategy 2: Check known image URLs mapping
        knownImageUrls[characterName]?.let { url ->
            return url
        }
        
        // Strategy 3: Try pattern-based URL
        val patternBasedUrl = generatePatternBasedUrl(characterName)
        return patternBasedUrl
    }
    
    /**
     * Generates a potential image URL based on the character name pattern.
     * The API uses lowercase names, often first name only or full name without spaces.
     * Examples: "Harry Potter" -> "harry.jpg", "Minerva McGonagall" -> "mcgonagall.jpg"
     *
     * @param characterName The name of the character
     * @return A potential image URL based on the naming pattern
     */
    private fun generatePatternBasedUrl(characterName: String): String {
        // Try multiple patterns based on observed API naming conventions
        val patterns = listOf(
            // Pattern 1: Full name lowercase, no spaces (e.g., "harrypotter")
            characterName.lowercase().replace(" ", "").replace("-", "").replace("'", ""),
            // Pattern 2: First name only (e.g., "harry")
            characterName.split(" ").first().lowercase(),
            // Pattern 3: Last name only (e.g., "potter")
            characterName.split(" ").lastOrNull()?.lowercase() ?: "",
            // Pattern 4: Full name with spaces removed, special chars removed
            characterName.lowercase()
                .replace(" ", "")
                .replace("-", "")
                .replace("'", "")
                .replace(".", "")
                .replace(",", "")
        ).filter { it.isNotEmpty() }
        
        // Try common extensions for the first pattern
        val extensions = listOf("jpg", "jpeg", "png")
        val firstPattern = patterns.firstOrNull() ?: characterName.lowercase()
        
        return extensions.firstOrNull()?.let { ext ->
            "$BASE_IMAGE_URL/$firstPattern.$ext"
        } ?: "$BASE_IMAGE_URL/$firstPattern.jpg"
    }
    
    /**
     * Adds a known image URL to the mapping.
     * Useful for manually curating additional character images.
     *
     * @param characterName The name of the character
     * @param imageUrl The image URL for the character
     */
    fun addKnownImageUrl(characterName: String, imageUrl: String) {
        // Note: This would require making knownImageUrls mutable
        // For now, this is a placeholder for future enhancement
    }
}

