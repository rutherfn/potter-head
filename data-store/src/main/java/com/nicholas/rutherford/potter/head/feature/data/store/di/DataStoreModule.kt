package com.nicholas.rutherford.potter.head.feature.data.store.di

import com.nicholas.rutherford.potter.head.feature.data.store.reader.DataStorePreferenceReader
import com.nicholas.rutherford.potter.head.feature.data.store.writer.DataStorePreferencesWriter

interface DataStoreModule {

    val dataStorePreferenceReader: DataStorePreferenceReader
    val dataStorePreferenceWriter: DataStorePreferencesWriter
}