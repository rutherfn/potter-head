package com.nicholas.rutherford.potter.head.base.view.model

import androidx.lifecycle.ViewModelProvider

/**
 * Interface for providing ViewModelFactory.
 * Allows entry-point module to access ViewModelFactory without compile-time dependency on app module.
 * This avoids reflection by using a public interface contract.
 *
 * @author Nicholas Rutherford
 */
interface ViewModelFactoryProvider {
    fun getViewModelFactory(): ViewModelProvider.Factory
}
