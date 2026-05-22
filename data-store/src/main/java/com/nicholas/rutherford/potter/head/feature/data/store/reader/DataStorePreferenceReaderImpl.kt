package com.nicholas.rutherford.potter.head.feature.data.store.reader

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import com.nicholas.rutherford.potter.head.core.Constants
import com.nicholas.rutherford.potter.head.core.theme.ThemePreference
import com.nicholas.rutherford.potter.head.feature.data.store.dataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

/**
 * Implementation of [DataStorePreferenceReader] responsible for reading values from DataStore.
 * This class retrieves various user-related flags and data from persistent storage using
 * Android DataStore preferences.
 *
 * @param context The [Context] instance used to access the DataStore.
 *
 * @author Nicholas Rutherford
 */
class DataStorePreferenceReaderImpl(private val context: Context) : DataStorePreferenceReader {

    private var cachedThemePreferenceValue: Int? = null

    override fun readThemePreferenceValueFlow(): Flow<Int> {
        return context.dataStore.data.map { preferences ->
            preferences[intPreferencesKey(name = Constants.DataStore.VALUES.THEME_PREFERENCE)] ?: ThemePreference.SYSTEM.value
        }
    }

    override suspend fun readThemePreferenceSnapshot(): Int {
        return cachedThemePreferenceValue ?: readThemePreferenceValueFlow().first().also { value -> cachedThemePreferenceValue = value }
    }

    override fun readShouldShuffleAnswerOrderFlow(): Flow<Boolean> {
        return context.dataStore.data.map { preferences ->
            preferences[booleanPreferencesKey(name = Constants.DataStore.VALUES.SHOULD_SHUFFLE_ANSWER_ORDER)] ?: false
        }
    }

}