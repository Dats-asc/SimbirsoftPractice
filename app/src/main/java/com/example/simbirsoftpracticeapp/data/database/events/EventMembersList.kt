package com.example.simbirsoftpracticeapp.data.database.events

import com.example.simbirsoftpracticeapp.news.data.EventMember
import com.google.gson.annotations.SerializedName

data class EventMembersList(
    @SerializedName("events")
    val list: List<EventMember>
)