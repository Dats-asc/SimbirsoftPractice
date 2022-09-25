package com.example.simbirsoftpracticeapp.di.module

import android.content.Context
import com.example.simbirsoftpracticeapp.data.database.AppDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(includes = [DaoModule::class])
class RoomModule {

    @Provides
    @Singleton
    fun provideDatabase(
        context: Context
    ): AppDatabase {
        return AppDatabase(context)
    }

}