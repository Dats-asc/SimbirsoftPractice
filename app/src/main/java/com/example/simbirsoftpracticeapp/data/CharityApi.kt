package com.example.simbirsoftpracticeapp.data

import com.example.simbirsoftpracticeapp.data.entity.CategoriesResponse
import com.example.simbirsoftpracticeapp.data.entity.EventsResponse
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface CharityApi {

    @GET("events")
    fun getEvents(): Single<EventsResponse>

    @GET("events")
    fun getEventById(@Query("id") id: Int): Single<EventsResponse>

    @GET("categories")
    fun getCategories(): Single<CategoriesResponse>

    @GET("events")
    fun searchEvents(@Query("q") request: String): Single<EventsResponse>
}