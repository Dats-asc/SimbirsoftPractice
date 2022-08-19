package com.example.simbirsoftpracticeapp.di.module

import com.example.simbirsoftpracticeapp.presentation.help.HelpFragment
import com.example.simbirsoftpracticeapp.presentation.news.NewsDetailFragment
import com.example.simbirsoftpracticeapp.presentation.news.NewsFilterFragment
import com.example.simbirsoftpracticeapp.presentation.news.NewsFragment
import com.example.simbirsoftpracticeapp.presentation.profile.ProfileFragment
import com.example.simbirsoftpracticeapp.presentation.search.ByNkoFragment
import com.example.simbirsoftpracticeapp.presentation.search.EventsTabFragment
import com.example.simbirsoftpracticeapp.presentation.search.SearchFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentModule {

    @ContributesAndroidInjector
    abstract fun contributeNewsFilterFragment(): NewsFilterFragment

    @ContributesAndroidInjector
    abstract fun contributeProfileFragment(): ProfileFragment

    @ContributesAndroidInjector
    abstract fun contributeNewsDetailFragment(): NewsDetailFragment

    @ContributesAndroidInjector
    abstract fun contributeNewsFragment(): NewsFragment

    @ContributesAndroidInjector
    abstract fun contributeHelpFragment(): HelpFragment

    @ContributesAndroidInjector
    abstract fun contributeSearchFragment(): SearchFragment

    @ContributesAndroidInjector
    abstract fun contributeBuNkoFragment(): ByNkoFragment

    @ContributesAndroidInjector
    abstract fun contributeEventsTabFragment(): EventsTabFragment
}