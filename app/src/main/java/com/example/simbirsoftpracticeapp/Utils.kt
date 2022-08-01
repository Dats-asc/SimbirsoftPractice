package com.example.simbirsoftpracticeapp

import android.content.Context
import android.util.Log
import com.example.simbirsoftpracticeapp.news.data.CharityEvents
import com.example.simbirsoftpracticeapp.news.data.FilterCategories
import com.google.gson.Gson

object Utils {

    private const val CATEGORIES_JSON = "categories.json"
    private const val CHARITY_EVENTS_JSON = "charity_events.json"

    fun getCategories(appContext: Context): FilterCategories {
        try {
            val data = appContext.assets
                .open(CATEGORIES_JSON)
                .bufferedReader()

            val gson = Gson()
            return gson.fromJson(data, FilterCategories::class.java)
        } catch (e: Exception) {
            Log.e("", e.message.orEmpty())
            throw e
        }
    }

    fun getEvents(appContext: Context): CharityEvents {
        try {
            val data = appContext.assets
                .open(CHARITY_EVENTS_JSON)
                .bufferedReader()

            val gson = Gson()
            val result = gson.fromJson(data, CharityEvents::class.java)
            return result
        } catch (e: Exception) {
            Log.e("", e.message.orEmpty())
            throw e
        }
    }

}