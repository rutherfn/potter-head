package com.nicholas.rutherford.potter.head.network

import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import androidx.core.content.ContextCompat
import co.touchlab.kermit.Logger
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

/**
 * Implementation of [NetworkMonitor] that monitors network connectivity using Android's ConnectivityManager.
 * Provides reactive updates via Flow and synchronous checks for current connectivity status.
 *
 * @param context The application context for checking permissions.
 * @param connectivityManager The ConnectivityManager instance for checking network state.
 *
 * @author Nicholas Rutherford
 */
class NetworkMonitorImpl(
    private val context: Context,
    private val connectivityManager: ConnectivityManager
) : NetworkMonitor {
    private val log = Logger.withTag(tag = "NetworkMonitorImpl")

    /**
     * A Flow that emits the current network connectivity status.
     * Automatically updates when connectivity changes.
     * Falls back to polling if network callbacks are not available due to missing permissions.
     * Here we are also suppressing the lint of MissingPermission, since we do automatically check for permission
     */
    @SuppressLint("MissingPermission")
    override val isConnected: Flow<Boolean> = callbackFlow {
        val hasPermission = ContextCompat.checkSelfPermission(
            context,
            android.Manifest.permission.ACCESS_NETWORK_STATE
        ) == PackageManager.PERMISSION_GRANTED

        if (!hasPermission) {
            // If no permission, just send false and close
            trySend(false)
            awaitClose { }
            log.e("No permission to access network state - sending false")
            return@callbackFlow
        }

        val callback = object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                val isConnected = connectivityManager.isCurrentlyConnected()
                trySend(isConnected)
            }

            override fun onLost(network: Network) {
                log.e("Network lost - sending false")
                trySend(false)
            }

            override fun onCapabilitiesChanged(
                network: Network,
                networkCapabilities: NetworkCapabilities
            ) {
                val isConnected = connectivityManager.isCurrentlyConnected()
                trySend(isConnected)
            }
        }

        val request = NetworkRequest.Builder()
            .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            .addCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED)
            .build()

        try {
            connectivityManager.registerNetworkCallback(request, callback)

            trySend(connectivityManager.isCurrentlyConnected())

            awaitClose {
                try {
                    connectivityManager.unregisterNetworkCallback(callback)
                } catch (e: Exception) {
                    log.e("Failed to unregister network callback with error: ${e.message}")
                }
            }
        } catch (e: SecurityException) {
            log.e("Failed to register network callback with error: ${e.message}")
            trySend(false)
            awaitClose { }
        }
    }

    /**
     * Checks if the device is currently connected to the internet.
     * This checks both network availability and internet capability.
     *
     * @return true if connected to the internet, false otherwise.
     */
    override suspend fun isConnected(): Boolean {
        val hasPermission = ContextCompat.checkSelfPermission(
            context,
            android.Manifest.permission.ACCESS_NETWORK_STATE
        ) == PackageManager.PERMISSION_GRANTED

        if (!hasPermission) {
            return false
        }

        return try {
            connectivityManager.isCurrentlyConnected()
        } catch (e: SecurityException) {
            log.e("Failed to check network state with error: ${e.message}")
            false
        }
    }

    /**
     * Extension function to check if ConnectivityManager indicates a valid internet connection.
     * Checks both that a network is active and that it has internet capability and validation.
     * Here we are also suppressing the lint of MissingPermission, since we do automatically check for permission
     *
     * @return true if connected to the internet, false otherwise.
     */
    @SuppressLint("MissingPermission")
    private fun ConnectivityManager.isCurrentlyConnected(): Boolean {
        val activeNetwork = activeNetwork ?: return false
        val capabilities = getNetworkCapabilities(activeNetwork) ?: return false
        return capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) &&
                capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED)
    }
}
