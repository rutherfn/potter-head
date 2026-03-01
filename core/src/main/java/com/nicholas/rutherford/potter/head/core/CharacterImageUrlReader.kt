package com.nicholas.rutherford.potter.head.core

import android.content.Context
import co.touchlab.kermit.Logger
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

/**
 * Utility class for reading character image URLs from the JSON file.
 *
 * @author Nicholas Rutherford
 */
object CharacterImageUrlReader {

    /**
     * Kermit Logger for this class.
     */
    private val log = Logger.withTag(tag = "CharacterImageUrlReader")

    /**
     * Gson instance for JSON parsing.
     */
    private val gson: Gson by lazy { Gson() }

    /**
     * Reads the characters_images_url.json file from raw resources and returns a list of [CharacterImagesUrlJsonResponse].
     *
     * @param context The Android context to access resources.
     * @return A list of [CharacterImagesUrlJsonResponse] objects, or an empty list if reading/parsing fails.
     */
    fun getCharacterImageUrls(context: Context): List<CharacterImagesUrlJsonResponse> {
        return try {
            val inputStream = context.resources.openRawResource(R.raw.characters_images_url)
            val jsonString = inputStream.bufferedReader().use { it.readText() }
            
            val listType = object : TypeToken<List<CharacterImagesUrlJsonResponse>>() {}.type
            val characterImageUrls = gson.fromJson<List<CharacterImagesUrlJsonResponse>>(jsonString, listType)

            characterImageUrls.ifEmpty {
                log.w("Read character image URLS from JSON but was empty")
                emptyList()
            }
        } catch (e: Exception) {
            log.e("Failed to read character image URLs from JSON: ${e.message}")
            emptyList()
        }
    }
}

