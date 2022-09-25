package com.example.simbirsoftpracticeapp.di.module

import com.example.simbirsoftpracticeapp.di.scope.ActivityScope
import com.example.simbirsoftpracticeapp.presentation.main.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityModule {

    @ActivityScope
    @ContributesAndroidInjector
    abstract fun contributeMainActivity(): MainActivity
}