package com.example.farmaglobal.Network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

//setup API Client
object ApiClient {
    private fun provideOkhttp(): OkHttpClient {
        return OkHttpClient().newBuilder()
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build()
    }

    private fun provideApi(): Retrofit {
        return Retrofit.Builder()
            .client(provideOkhttp())
            .baseUrl("https://androidtest.farmagitechs.co.id/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val api: ApiServices = provideApi().create(ApiServices::class.java)
}