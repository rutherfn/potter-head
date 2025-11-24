package com.nicholas.rutherford.potter.head.saved.state.di

import com.nicholas.rutherford.potter.head.saved.state.SavedStateHandleFactory
import dev.zacsweers.metro.DependencyGraph
import dev.zacsweers.metro.Provides

/**
 * Metro dependency graph for saved state-related components.
 * Provides access to [SavedStateHandleFactory] for creating SavedStateHandle instances.
 *
 * @author Nicholas Rutherford
 */
@DependencyGraph
interface SavedStateModule {

    /**
     * Provides access to [SavedStateHandleFactory] for creating SavedStateHandle instances.
     */
    val savedStateHandleFactory: SavedStateHandleFactory

    companion object {
        /**
         * Provides an instance of [SavedStateHandleFactory].
         *
         * @return The singleton [SavedStateHandleFactory] instance.
         */
        @Provides
        fun provideSavedStateHandleFactory(): SavedStateHandleFactory = SavedStateHandleFactory
    }
}

