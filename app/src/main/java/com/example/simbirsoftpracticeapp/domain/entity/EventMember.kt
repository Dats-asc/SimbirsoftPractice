package com.example.simbirsoftpracticeapp.domain.entity

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class EventMember(
    @SerializedName("id")
    val id: Int,
    @SerializedName("icon_src")
    val iconSrc: String
) : Serializable
