package com.nicholas.rutherford.potter.head.network

import kotlinx.coroutines.flow.Flow

/**
 * Interface for monitoring network connectivity.
 * Provides reactive and synchronous ways to check internet connectivity status.
 *
 * @author Nicholas Rutherford
 */
interface NetworkMonitor {
    /**
     * A Flow that emits the current network connectivity status.
     * Emits true when connected to the internet, false when disconnected.
     * Automatically updates when connectivity changes.
     *
     * @return A [Flow] emitting [Boolean] representing connectivity status.
     */
    val isConnected: Flow<Boolean>

    /**
     * Checks if the device is currently connected to the internet.
     * This is a synchronous check that returns the current state.
     *
     * @return true if connected to the internet, false otherwise.
     */
    suspend fun isConnected(): Boolean
}
