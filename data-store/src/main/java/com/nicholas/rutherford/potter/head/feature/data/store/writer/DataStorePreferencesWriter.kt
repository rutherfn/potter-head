package com.nicholas.rutherford.potter.head.feature.data.store.writer

/**
 * Interface defining operations for writing preference values to DataStore.
 * This interface provides methods to save various user-related flags and data
 * into persistent storage using Android DataStore.
 *
 * @author Nicholas Rutherford
 */
interface DataStorePreferencesWriter {
    suspend fun saveThemePreferenceValue(value: Int)
    suspend fun saveShouldShuffleAnswerOrder(value: Boolean)
}