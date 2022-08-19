package com.example.simbirsoftpracticeapp.domain.repository

import com.example.simbirsoftpracticeapp.domain.entity.CharityEvent
import com.example.simbirsoftpracticeapp.domain.entity.CharityEvents
import com.example.simbirsoftpracticeapp.domain.entity.FilterCategories
import com.example.simbirsoftpracticeapp.domain.entity.FilterCategory
import com.example.simbirsoftpracticeapp.presentation.search.SearchResult
import dagger.Module
import io.reactivex.rxjava3.core.Single

@Module
interface CharityRepository {

    fun getCategories(): Single<FilterCategories>

    fun getEventById(id: Int): Single<CharityEvent>

    fun getEvents(): Single<CharityEvents>

    fun updateCategories(categories: List<FilterCategory>)

    fun searchEvents(request: String): Single<List<CharityEvent>>
}