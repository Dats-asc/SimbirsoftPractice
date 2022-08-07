package com.example.simbirsoftpracticeapp

import android.content.Context
import android.util.Log
import com.example.simbirsoftpracticeapp.news.data.CharityEvents
import com.example.simbirsoftpracticeapp.news.data.FilterCategories
import com.google.gson.Gson
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.internal.schedulers.RxThreadFactory
import io.reactivex.rxjava3.schedulers.Schedulers
import org.threeten.bp.Instant
import org.threeten.bp.Month
import org.threeten.bp.ZoneId
import org.threeten.bp.ZonedDateTime
import java.util.*
import java.util.concurrent.TimeUnit

object Utils {

    private const val CATEGORIES_JSON = "categories.json"
    private const val CHARITY_EVENTS_JSON = "charity_events.json"

    fun getCategoriesRxJava(appContext: Context): Observable<FilterCategories> {
        return Observable.create<FilterCategories?> { subscriber ->
            try {
                val data = appContext.assets
                    .open(CATEGORIES_JSON)
                    .bufferedReader()

                val gson = Gson()
                val result = gson.fromJson(data, FilterCategories::class.java)
                subscriber.onNext(result)
                Log.e("Current thread is ", Thread.currentThread().name)
                subscriber.onComplete()
            } catch (e: Exception) {
                Log.e("", e.message.orEmpty())
                throw e
            }
        }
            .subscribeOn(Schedulers.newThread())
    }

    fun getEventsRxJava(appContext: Context): Observable<CharityEvents> {
        return Observable.create<CharityEvents> {
            try {
                val data = appContext.assets
                    .open(CHARITY_EVENTS_JSON)
                    .bufferedReader()

                val gson = Gson()
                val result = gson.fromJson(data, CharityEvents::class.java)
                Log.e("Current thread is ", Thread.currentThread().name)
                it.onNext(result)
                it.onComplete()
            } catch (e: Exception) {
                Log.e("", e.message.orEmpty())
                it.onError(e)
            }
        }
            .subscribeOn(Schedulers.newThread())
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