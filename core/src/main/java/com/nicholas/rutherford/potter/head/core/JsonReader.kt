package com.nicholas.rutherford.potter.head.core

import android.content.Context
import co.touchlab.kermit.Logger
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.nicholas.rutherford.potter.head.core.jsonresponse.QuizJsonResponse

/**
 * Utility class for reading JSON files from raw resources.
 *
 * @author Nicholas Rutherford
 */
object JsonReader {

    /**
     * Kermit Logger for this class.
     */
    private val log = Logger.withTag(tag = "JsonReader")

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
                log.w(messageString = "Read character image URLs from JSON but was empty")
                emptyList()
            }
        } catch (e: Exception) {
            log.e(messageString = "Failed to read character image URLs from JSON: ${e.message}")
            emptyList()
        }
    }

    /**
     * Reads the app_quizzes.json file from raw resources and returns a list of [QuizJsonResponse].
     *
     * @param context The Android context to access resources.
     * @return A list of [QuizJsonResponse] objects, or an empty list if reading/parsing fails.
     */
    fun getQuizzes(context: Context): List<QuizJsonResponse> {
        return try {
            val inputStream = context.resources.openRawResource(R.raw.app_quizzes)
            val jsonString = inputStream.bufferedReader().use { it.readText() }
            
            log.d(messageString = "Successfully read JSON string, length: ${jsonString.length}")
            
            val listType = object : TypeToken<List<QuizJsonResponse>>() {}.type
            val quizzes = gson.fromJson<List<QuizJsonResponse>>(jsonString, listType)

            quizzes.ifEmpty {
                log.w(messageString = "Read quizzes from JSON but was empty")
                emptyList()
            }
        } catch (e: Exception) {
            log.e(messageString = "Failed to read quizzes from JSON: ${e.message}", throwable = e)
            emptyList()
        }
    }
}
