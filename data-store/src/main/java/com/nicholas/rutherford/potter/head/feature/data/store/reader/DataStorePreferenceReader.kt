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
    fun readThemePreferenceValueFlow(): Flow<Int>

    /**
     * Loads the theme preference from disk once and caches it for synchronous reads.
     * Call from [android.app.Application.onCreate] before the first activity is shown.
     */
    suspend fun ensureThemePreferenceLoaded()

    /**
     * Returns the cached theme preference after [ensureThemePreferenceLoaded], otherwise [com.nicholas.rutherford.potter.head.core.theme.ThemePreference.SYSTEM].
     */
    fun peekThemePreferenceValue(): Int

    fun readShouldShuffleAnswerOrderFlow(): Flow<Boolean>
}