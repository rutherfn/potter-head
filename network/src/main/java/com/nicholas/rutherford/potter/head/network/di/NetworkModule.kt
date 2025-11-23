package com.nicholas.rutherford.potter.head.network.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.Strictness
import com.nicholas.rutherford.potter.head.core.Constants
import com.nicholas.rutherford.potter.head.network.HarryPotterApiRepository
import com.nicholas.rutherford.potter.head.network.HarryPotterApiRepositoryImpl
import com.nicholas.rutherford.potter.head.network.HarryPotterApiService
import dev.zacsweers.metro.DependencyGraph
import dev.zacsweers.metro.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Dependency graph for network-related components.
 * Provides Retrofit, Gson, API services, and repositories for the network layer.
 *
 * @author Nicholas Rutherford
 */
@DependencyGraph
interface NetworkModule {

    /**
     * Gson instance for JSON serialization/deserialization.
     */
    @Suppress("unused")
    val gson: Gson

    /**
     * Retrofit instance configured with base URL and Gson converter.
     */
    @Suppress("unused")
    val retrofit: Retrofit

    /**
     * API service for the [Harry Potter API](https://hp-api.onrender.com/).
     */
    @Suppress("unused")
    val harryPotterApiService: HarryPotterApiService

    /**
     * Repository for fetching character data from the API.
     */
    val harryPotterApiRepository: HarryPotterApiRepository
    
    companion object {

        /**
         * Provides a Gson instance configured with lenient parsing.
         *
         * @return A [Gson] instance with lenient strictness for flexible JSON parsing.
         */
        @Suppress("unused")
        @Provides
        fun provideGson(): Gson {
            return GsonBuilder()
                .setStrictness(Strictness.LENIENT)
                .create()
        }

        /**
         * Provides a Retrofit instance configured with base URL and Gson converter.
         *
         * @param gson The [Gson] instance to use for JSON conversion.
         * @return A [Retrofit] instance configured for the Harry Potter API.
         */
        @Suppress("unused")
        @Provides
        fun provideRetrofit(gson: Gson): Retrofit = Retrofit.Builder()
                .baseUrl(Constants.BASE_API_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()

        /**
         * Provides the [HarryPotterApiService] implementation using Retrofit.
         *
         * @param retrofit The [Retrofit] instance to create the service from.
         * @return A [HarryPotterApiService] implementation.
         */
        @Suppress("unused")
        @Provides
        fun provideHarryPotterApiService(retrofit: Retrofit): HarryPotterApiService = retrofit.create(HarryPotterApiService::class.java)

        /**
         * Provides the [HarryPotterApiRepository] implementation.
         *
         * @param apiService The [HarryPotterApiService] to use for API calls.
         * @return A [HarryPotterApiRepository] implementation.
         */
        @Suppress("unused")
        @Provides
        fun provideHarryPotterApiRepository(
            apiService: HarryPotterApiService
        ): HarryPotterApiRepository = HarryPotterApiRepositoryImpl(apiService = apiService)
    }

}
