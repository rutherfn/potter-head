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
    val viewModelScope: CoroutineScope

    val ioScope: CoroutineScope

    val mainScope: CoroutineScope

    val defaultScope: CoroutineScope
}

