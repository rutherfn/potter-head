package com.nicholas.rutherford.potter.head.di

import android.content.Context
import com.nicholas.rutherford.potter.head.feature.data.store.di.DataStoreModule
import com.nicholas.rutherford.potter.head.feature.data.store.reader.DataStorePreferenceReader
import com.nicholas.rutherford.potter.head.feature.data.store.reader.DataStorePreferenceReaderImpl
import com.nicholas.rutherford.potter.head.feature.data.store.writer.DataStorePreferencesWriter
import com.nicholas.rutherford.potter.head.feature.data.store.writer.DataStorePreferencesWriterImpl

/**
 * Implementation of [DataStoreModule].
 * Provides the data store dependency which includes the
 * [DataStorePreferenceReader] and [DataStorePreferencesWriter]
 *
 * @param context The application context for creating the data store.
 *
 * @author Nicholas Rutherford
 */
internal class DataStoreModuleImpl(
    private val context: Context
) : DataStoreModule {
    override val dataStorePreferenceReader: DataStorePreferenceReader by lazy { DataStorePreferenceReaderImpl(context = context) }

    override val dataStorePreferenceWriter: DataStorePreferencesWriter by lazy { DataStorePreferencesWriterImpl(context = context) }
}
