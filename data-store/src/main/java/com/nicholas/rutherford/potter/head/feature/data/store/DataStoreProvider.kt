package com.nicholas.rutherford.potter.head.feature.data.store

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.nicholas.rutherford.potter.head.core.Constants

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = Constants.DataStore.NAME)