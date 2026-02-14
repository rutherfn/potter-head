package com.nicholas.rutherford.potter.head.network.di

import com.google.gson.Gson
import com.nicholas.rutherford.potter.head.network.HarryPotterApiRepository
import com.nicholas.rutherford.potter.head.network.HarryPotterApiService
import retrofit2.Retrofit

/**
 * Dependency graph interface for network-related components.
 * Provides Retrofit, Gson, API services, and repositories for the network layer.
 *
 * @author Nicholas Rutherford
 */
interface NetworkModule {

    /**
     * Gson instance for JSON serialization/deserialization.
     */
    val gson: Gson

    /**
     * Retrofit instance configured with base URL and Gson converter.
     */
    val retrofit: Retrofit

    /**
     * API service for the [Harry Potter API](https://hp-api.onrender.com/).
     */
    val harryPotterApiService: HarryPotterApiService

    /**
     * Repository for fetching character data from the API.
     */
    val harryPotterApiRepository: HarryPotterApiRepository
}
