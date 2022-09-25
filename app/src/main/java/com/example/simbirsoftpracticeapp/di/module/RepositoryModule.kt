package com.example.simbirsoftpracticeapp.di.module

import com.example.simbirsoftpracticeapp.data.CharityRepositoryImpl
import com.example.simbirsoftpracticeapp.domain.repository.CharityRepository
import dagger.Binds
import dagger.Module

@Module
interface RepositoryModule {

    @Binds
    fun charityRepository(impl: CharityRepositoryImpl) : CharityRepository
}