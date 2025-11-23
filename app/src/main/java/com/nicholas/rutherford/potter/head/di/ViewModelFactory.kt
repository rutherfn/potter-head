package com.nicholas.rutherford.potter.head.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.nicholas.rutherford.potter.head.entry.point.MainActivityViewModel

/**
 * Factory for creating ViewModels with Metro dependency injection.
 * 
 * This factory uses AppGraph from Application to provide dependencies.
 * Following Metro best practices, dependencies are injected via constructor injection.
 * 
 * @param appGraph The root dependency graph from Application, providing access to all modules.
 */
class ViewModelFactory(
    private val appGraph: AppGraph
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(MainActivityViewModel::class.java) -> {
                MainActivityViewModel(
                    repository = appGraph.networkModule.harryPotterApiRepository
                ) as T
            }
            else -> throw IllegalArgumentException(
                "Unknown ViewModel class: ${modelClass.name}. " +
                "Add it to ViewModelFactory.create() to support injection."
            )
        }
    }
}

