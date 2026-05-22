package com.nicholas.rutherford.potter.head.feature.data.store.reader

import kotlinx.coroutines.flow.Flow

/**
 * Interface defining operations for reading preference values from DataStore.
 * This interface provides methods to retrieve various user-related flags and data
 * from persistent storage using Android DataStore.
 *
 * @author Nicholas Rutherford
 */
interface DataStorePreferenceReader {
    suspend fun readThemePreferenceSnapshot(): Int
    fun readThemePreferenceValueFlow(): Flow<Int>
    fun readShouldShuffleAnswerOrderFlow(): Flow<Boolean>
}