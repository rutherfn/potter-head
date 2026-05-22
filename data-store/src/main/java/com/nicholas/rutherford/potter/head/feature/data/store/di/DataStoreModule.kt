package com.nicholas.rutherford.potter.head.feature.data.store.di

import com.nicholas.rutherford.potter.head.feature.data.store.reader.DataStorePreferenceReader
import com.nicholas.rutherford.potter.head.feature.data.store.writer.DataStorePreferencesWriter

/**
 * Dependency graph interface for data store components
 * Provides access to [dataStorePreferenceReader] and [DataStorePreferencesWriter]
 *
 * @author Nicholas Rutherford
 */
interface DataStoreModule {
    val dataStorePreferenceReader: DataStorePreferenceReader
    val dataStorePreferenceWriter: DataStorePreferencesWriter
}