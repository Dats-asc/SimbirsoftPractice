package com.example.simbirsoftpracticeapp.di

import com.example.simbirsoftpracticeapp.App
import com.example.simbirsoftpracticeapp.di.module.*
import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        AppModule::class,
        ActivityModule::class,
        FragmentModule::class,
        NetModule::class,
        RepositoryModule::class,
        RoomModule::class
    ]
)
interface AppComponent {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun context(application: App): Builder

        fun build(): AppComponent
    }

    fun inject(application: App)
}