package com.example.simbirsoftpracticeapp.data

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class CharityRepository {

    private val rxAdapter = RxJava3CallAdapterFactory.create()
    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        setLevel(HttpLoggingInterceptor.Level.BASIC)
        setLevel(HttpLoggingInterceptor.Level.BODY)
    }
    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .build()
    private val gsonConverter = GsonConverterFactory.create()

    fun charityApi() = Retrofit.Builder()
        .baseUrl(CHARITY_API_BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(gsonConverter)
        .addCallAdapterFactory(rxAdapter)
        .build()
        .create(CharityApi::class.java)

    companion object {
        private const val CHARITY_API_BASE_URL = "http://10.0.2.2:3000/"
    }
}