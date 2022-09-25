package com.example.simbirsoftpracticeapp.di.module

import com.example.simbirsoftpracticeapp.data.database.AppDatabase
import com.example.simbirsoftpracticeapp.data.database.categories.CategoriesDao
import com.example.simbirsoftpracticeapp.data.database.events.EventsDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DaoModule {

    @Provides
    @Singleton
    fun provideCategoriesDao(
        database: AppDatabase
    ): CategoriesDao = database.categoriesDao()

    @Provides
    fun provideEventsDao(
        database: AppDatabase
    ): EventsDao = database.charityEventsDao()
}