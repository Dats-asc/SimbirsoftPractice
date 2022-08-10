package com.example.simbirsoftpracticeapp.main

import com.example.simbirsoftpracticeapp.Subjects

interface Readable {

    val subject: List<Subjects.Subject>

    fun setNotificationBadge(count: Int)

    fun setAsRead(id: Int)
}