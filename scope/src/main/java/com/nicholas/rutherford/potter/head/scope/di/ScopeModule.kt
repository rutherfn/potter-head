package com.nicholas.rutherford.potter.head.scope.di

import kotlinx.coroutines.CoroutineScope

/**
 * Dependency graph interface for coroutine scope-related components.
 * Provides different coroutine scopes for various use cases throughout the application.
 *
 * This module centralizes scope management, making it easier to:
 * - Inject test scopes for unit testing
 * - Manage scope lifecycle
 * - Provide different scopes for different use cases (IO, Main, Default, etc.)
 *
 * @author Nicholas Rutherford
 */
interface ScopeModule {
    /**
     * Coroutine scope for ViewModel operations.
     * Uses Dispatchers.Default as the base dispatcher, which is optimal for most
     * ViewModel operations. Switch to Dispatchers.Main when UI updates are needed,
     * or use Dispatchers.IO for I/O operations.
     * Can be injected for testing purposes.
     */
    val viewModelScope: CoroutineScope

    /**
     * Coroutine scope for IO operations (database, network, file I/O).
     * Uses Dispatchers.IO for optimal performance on background threads.
     */
    val ioScope: CoroutineScope

    /**
     * Coroutine scope for main/UI thread operations.
     * Uses Dispatchers.Main for operations that need to update the UI.
     */
    val mainScope: CoroutineScope

    /**
     * Coroutine scope for default operations.
     * Uses Dispatchers.Default for CPU-intensive work.
     */
    val defaultScope: CoroutineScope
}

