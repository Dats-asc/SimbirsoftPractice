package com.example.simbirsoftpracticeapp.news.data

import com.google.gson.annotations.SerializedName

data class EventMember(
    @SerializedName("id")
    val id: Int,
    @SerializedName("icon_src")
    val iconSrc: String
)
