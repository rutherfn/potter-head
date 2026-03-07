package com.nicholas.rutherford.potter.head.di

import com.nicholas.rutherford.potter.head.scope.di.ScopeModule
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob

/**
 * Implementation of [ScopeModule].
 * Provides different coroutine scopes for various use cases throughout the application.
 *
 * @author Nicholas Rutherford
 */
class ScopeModuleImpl : ScopeModule {
    override val viewModelScope: CoroutineScope by lazy { CoroutineScope(SupervisorJob() + Dispatchers.Default) }
    override val ioScope: CoroutineScope by lazy { CoroutineScope(SupervisorJob() + Dispatchers.IO) }
    override val mainScope: CoroutineScope by lazy { CoroutineScope(SupervisorJob() + Dispatchers.Main) }
    override val defaultScope: CoroutineScope by lazy { CoroutineScope(SupervisorJob() + Dispatchers.Default) }
}
