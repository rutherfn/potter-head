package com.nicholas.rutherford.potter.head.network.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.Strictness
import com.nicholas.rutherford.potter.head.network.HarryPotterApiRepository
import com.nicholas.rutherford.potter.head.network.HarryPotterApiRepositoryImpl
import com.nicholas.rutherford.potter.head.network.HarryPotterApiService
import dev.zacsweers.metro.DependencyGraph
import dev.zacsweers.metro.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@DependencyGraph
interface NetworkModule {

    @Suppress("unused")
    val gson: Gson

    @Suppress("unused")
    val retrofit: Retrofit

    @Suppress("unused")
    val harryPotterApiService: HarryPotterApiService
    val harryPotterApiRepository: HarryPotterApiRepository
    
    companion object {
        private const val BASE_URL = "https://hp-api.onrender.com/api/"

        @Suppress("unused")
        @Provides
        fun provideGson(): Gson {
            return GsonBuilder()
                .setStrictness(Strictness.LENIENT)
                .create()
        }

        @Suppress("unused")
        @Provides
        fun provideRetrofit(gson: Gson): Retrofit {
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
        }

        @Suppress("unused")
        @Provides
        fun provideHarryPotterApiService(retrofit: Retrofit): HarryPotterApiService {
            return retrofit.create(HarryPotterApiService::class.java)
        }

        @Suppress("unused")
        @Provides
        fun provideHarryPotterApiRepository(
            apiService: HarryPotterApiService
        ): HarryPotterApiRepository {
            return HarryPotterApiRepositoryImpl(apiService)
        }
    }
}

