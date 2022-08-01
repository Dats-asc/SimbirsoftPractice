package com.example.simbirsoftpracticeapp

import android.content.Context
import android.util.Log
import com.example.simbirsoftpracticeapp.news.data.CharityEvents
import com.example.simbirsoftpracticeapp.news.data.FilterCategories
import com.google.gson.Gson
import org.threeten.bp.Instant
import org.threeten.bp.Month
import org.threeten.bp.ZoneId
import org.threeten.bp.ZonedDateTime

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

    fun getFormatedDate(date: String): String {
        val now = ZonedDateTime.ofInstant(Instant.now(), ZoneId.systemDefault())
        val eventDate = ZonedDateTime.parse(date)
        if (eventDate.dayOfYear - now.dayOfYear < 30) {
            val month =
                if (eventDate.monthValue < 10) "0${eventDate.monthValue}" else eventDate.monthValue
            return "Осталось ${eventDate.dayOfYear - now.dayOfYear} дней (${eventDate.dayOfMonth}.${month})"
        } else {
            val month = when (eventDate.month) {
                Month.JANUARY -> "Январь"
                Month.FEBRUARY -> "Февраль"
                Month.MARCH -> "Март"
                Month.APRIL -> "Апрель"
                Month.MAY -> "Май"
                Month.JUNE -> "Июнь"
                Month.JULY -> "Июль"
                Month.AUGUST -> "Август"
                Month.SEPTEMBER -> "Сентябрь"
                Month.OCTOBER -> "Октябрь"
                Month.NOVEMBER -> "Ноябрь"
                else -> ""
            }
            return "$month ${eventDate.dayOfMonth}, ${eventDate.year}"
        }
    }

}