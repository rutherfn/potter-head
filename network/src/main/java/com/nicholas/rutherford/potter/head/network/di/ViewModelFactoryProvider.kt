package com.nicholas.rutherford.potter.head.network.di

import androidx.lifecycle.ViewModelProvider

/**
 * Interface for providing ViewModelFactory.
 * Allows entry-point module to access ViewModelFactory without compile-time dependency on app module.
 * This avoids reflection by using a public interface contract.
 */
interface ViewModelFactoryProvider {
    fun getViewModelFactory(): ViewModelProvider.Factory
}
