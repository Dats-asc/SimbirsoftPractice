package com.example.simbirsoftpracticeapp.di.module

import com.example.simbirsoftpracticeapp.data.CharityApi
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class NetModule {

    @Provides
    @Singleton
    fun provideGson(): Gson = Gson()

    @Provides
    @Singleton
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            setLevel(HttpLoggingInterceptor.Level.BASIC)
            setLevel(HttpLoggingInterceptor.Level.BODY)
        }
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(
        httpLoggingInterceptor: HttpLoggingInterceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .build()
    }

    @Provides
    @Singleton
    fun provideGsonConverter(): GsonConverterFactory = GsonConverterFactory.create()

    @Provides
    @Singleton
    fun provideRxAdapter(): RxJava3CallAdapterFactory = RxJava3CallAdapterFactory.create()

    @Provides
    @Singleton
    fun provideCharityApi(
        okHttpClient: OkHttpClient,
        gsonConverter: GsonConverterFactory,
        rxAdapter: RxJava3CallAdapterFactory
    ): CharityApi {
        return Retrofit.Builder()
            .baseUrl(CHARITY_API_BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(gsonConverter)
            .addCallAdapterFactory(rxAdapter)
            .build()
            .create(CharityApi::class.java)
    }

    companion object {
        private const val CHARITY_API_BASE_URL = "http://10.0.2.2:3000/"
    }
}