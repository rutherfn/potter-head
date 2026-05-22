package com.nicholas.rutherford.potter.head.feature.data.store

import com.nicholas.rutherford.potter.head.feature.data.store.reader.DataStorePreferenceReader

/**
 * Interface for providing [DataStorePreferenceReader] which is used in main Composable.
 * Allows entry-point module to access DataStorePreferenceReader without compile-time dependency on app module.
 *
 * @author Nicholas Rutherford
 */
interface DataStorePreferenceReaderProvider {
    fun getDataStorePreferenceReader(): DataStorePreferenceReader
}