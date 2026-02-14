package com.nicholas.rutherford.potter.head.saved.state.di

import com.nicholas.rutherford.potter.head.saved.state.SavedStateHandleFactory

/**
 * Dependency graph interface for saved state-related components.
 * Provides access to [SavedStateHandleFactory] for creating SavedStateHandle instances.
 *
 * @author Nicholas Rutherford
 */
interface SavedStateModule {
    /**
     * Provides access to [SavedStateHandleFactory] for creating SavedStateHandle instances.
     */
    val savedStateHandleFactory: SavedStateHandleFactory
}

