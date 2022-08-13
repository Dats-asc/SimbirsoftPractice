package com.example.simbirsoftpracticeapp.data

import com.example.simbirsoftpracticeapp.news.data.CharityEvent
import com.example.simbirsoftpracticeapp.news.data.FilterCategory
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface CharityApi {

    @GET("events")
    fun getEvents(): Single<List<CharityEvent>>

    @GET("events")
    fun getEventsBySelectedCategories(@Query("category_id") categories: List<Int>)

    @GET("events")
    fun getEventById(@Query("id") id: Int): Single<List<CharityEvent>>

    @GET("categories")
    fun getCategories(): Single<List<FilterCategory>>
}