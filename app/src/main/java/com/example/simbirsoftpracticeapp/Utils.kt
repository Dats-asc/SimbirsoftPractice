package com.example.simbirsoftpracticeapp

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.util.Log
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.simbirsoftpracticeapp.data.database.categories.CategoryEntity
import com.example.simbirsoftpracticeapp.data.database.events.CharityEventEntity
import com.example.simbirsoftpracticeapp.data.database.events.EventMembersList
import com.example.simbirsoftpracticeapp.data.database.events.PhoneNumbersList
import com.example.simbirsoftpracticeapp.news.data.*
import com.google.gson.Gson
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import org.threeten.bp.Instant
import org.threeten.bp.Month
import org.threeten.bp.ZoneId
import org.threeten.bp.ZonedDateTime

object Utils {

    private const val CATEGORIES_JSON = "categories.json"
    private const val CHARITY_EVENTS_JSON = "charity_events.json"

    private val gson = Gson()

    fun getCategories(appContext: Context): Observable<FilterCategories> {
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

    fun getEvents(appContext: Context): Observable<CharityEvents> {
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

    fun loadImage(view: ImageView, url: String) {

        Glide.with(view.context)
            .load(url)
            .placeholder(ColorDrawable(Color.LTGRAY))
            .error(R.drawable.ic_round_icon_placeholder)
            .diskCacheStrategy(DiskCacheStrategy.NONE)
            .skipMemoryCache(true)
            .into(view)
    }

    fun mapCharityEvent(event: CharityEvent): CharityEventEntity {
        return CharityEventEntity(
            id = event.id,
            title = event.title,
            date = event.date,
            charitableFoundation = event.charitableFoundation,
            address = event.address,
            phoneNumbers = gson.toJson(PhoneNumbersList(event.phoneNumbers)),
            img1 = event.img1,
            img2 = event.img2,
            img3 = event.img3,
            description = event.description,
            members = gson.toJson(EventMembersList(event.members)),
            membersCount = event.membersCount,
            sourceUrl = event.sourceUrl,
            email = event.email,
            categoryId = event.categoryId,
        )
    }

    fun mapCharityEventEntity(eventEntity: CharityEventEntity): CharityEvent {
        return CharityEvent(
            id = eventEntity.id,
            title = eventEntity.title,
            date = eventEntity.date,
            charitableFoundation = eventEntity.charitableFoundation,
            address = eventEntity.address,
            phoneNumbers = gson.fromJson(eventEntity.phoneNumbers, PhoneNumbersList::class.java).list,
            img1 = eventEntity.img1,
            img2 = eventEntity.img2,
            img3 = eventEntity.img3,
            description = eventEntity.description,
            members = gson.fromJson(eventEntity.members, EventMembersList::class.java).list,
            membersCount = eventEntity.membersCount,
            sourceUrl = eventEntity.sourceUrl,
            email = eventEntity.email,
            categoryId = eventEntity.categoryId
        )
    }

    fun mapCategory(category: FilterCategory): CategoryEntity {
        return CategoryEntity(
            id = category.id,
            title = category.title,
            isChecked = category.isChecked
        )
    }

    fun mapCategoryEntity(categoryEntity: CategoryEntity): FilterCategory {
        return FilterCategory(
            id = categoryEntity.id,
            title = categoryEntity.title,
            isChecked = categoryEntity.isChecked
        )
    }
}