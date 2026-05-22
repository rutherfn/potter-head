package com.nicholas.rutherford.potter.head.di

import android.content.Context
import android.net.ConnectivityManager
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.Strictness
import com.nicholas.rutherford.potter.head.core.Constants
import com.nicholas.rutherford.potter.head.network.HarryPotterApiRepository
import com.nicholas.rutherford.potter.head.network.HarryPotterApiRepositoryImpl
import com.nicholas.rutherford.potter.head.network.HarryPotterApiService
import com.nicholas.rutherford.potter.head.network.NetworkMonitor
import com.nicholas.rutherford.potter.head.network.NetworkMonitorImpl
import com.nicholas.rutherford.potter.head.network.di.NetworkModule
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Implementation of [NetworkModule].
 * Provides network-related dependencies including Retrofit, Gson, API services, and network monitoring.
 *
 * @param context The application context for accessing system services.
 *
 * @author Nicholas Rutherford
 */
class NetworkModuleImpl(
    private val context: Context
) : NetworkModule {
    private val connectivityManager: ConnectivityManager by lazy {
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    }

    override val gson: Gson by lazy {
        GsonBuilder()
            .setStrictness(Strictness.LENIENT)
            .create()
    }

    override val retrofit: Retrofit by lazy {
        Retrofit
            .Builder()
            .baseUrl(Constants.BASE_API_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    override val harryPotterApiService: HarryPotterApiService by lazy { retrofit.create(HarryPotterApiService::class.java) }

    override val harryPotterApiRepository: HarryPotterApiRepository by lazy {
        HarryPotterApiRepositoryImpl(
            apiService = harryPotterApiService
        )
    }

    override val networkMonitor: NetworkMonitor by lazy { NetworkMonitorImpl(context = context, connectivityManager = connectivityManager) }
}
