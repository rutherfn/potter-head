package com.nicholas.rutherford.potter.head.feature.data.store.writer

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import com.nicholas.rutherford.potter.head.core.Constants
import com.nicholas.rutherford.potter.head.feature.data.store.dataStore

/**
 * Implementation of [DataStorePreferencesWriter] responsible for saving values to DataStore.
 * This class writes various user-related flags and data into persistent storage using
 * Android DataStore preferences.
 *
 * @param context The [Context] instance used to access the DataStore.
 *
 * @author Nicholas Rutherford
 */
class DataStorePreferencesWriterImpl(private val context: Context): DataStorePreferencesWriter {

    override suspend fun saveThemePreferenceValue(value: Int) {
        context.dataStore.edit { preferences ->
            preferences[intPreferencesKey(name = Constants.DataStore.VALUES.THEME_PREFERENCE)] = value
        }
    }

    override suspend fun saveShouldShuffleAnswerOrder(value: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[booleanPreferencesKey(name = Constants.DataStore.VALUES.SHOULD_SHUFFLE_ANSWER_ORDER)] = value
        }
    }
}