package com.example.simbirsoftpracticeapp

import android.content.Context
import com.example.simbirsoftpracticeapp.news.data.CharityEvent
import io.reactivex.rxjava3.schedulers.Schedulers

class Subjects(appContext: Context) {

    var subjects: List<Subject>? = null

    init {
        Utils.getEvents(appContext)
            .map { events -> events.events.map { event -> Subject(event, false) } }
            .subscribeOn(Schedulers.io())
            .subscribe { subjects ->
                this.subjects = subjects
            }
    }

    fun setAsRead(id: Int) {
        subjects?.find { subject -> subject.event.id == id }?.isRead = true
    }

    data class Subject(
        val event: CharityEvent,
        var isRead: Boolean
    )
}