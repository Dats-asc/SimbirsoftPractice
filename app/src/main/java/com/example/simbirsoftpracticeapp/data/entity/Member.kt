package com.example.simbirsoftpracticeapp.data.entity

import com.google.gson.annotations.SerializedName

data class Member(
    @SerializedName("id")
    val id: Int,
    @SerializedName("icon_src")
    val icoSrc: String
)