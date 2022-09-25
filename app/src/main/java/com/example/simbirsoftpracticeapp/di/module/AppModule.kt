package com.example.simbirsoftpracticeapp.di.module

import android.content.Context
import com.example.simbirsoftpracticeapp.App
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule {

    @Provides
    @Singleton
    fun provideContext(application: App): Context = application.applicationContext
}