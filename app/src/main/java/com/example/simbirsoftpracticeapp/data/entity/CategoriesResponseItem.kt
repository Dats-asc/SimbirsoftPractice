package com.example.simbirsoftpracticeapp.data.entity

import com.google.gson.annotations.SerializedName

data class CategoriesResponseItem(
    @SerializedName("id")
    val id: Int,
    @SerializedName("is_checked")
    val isChecked: Boolean,
    @SerializedName("title")
    val title: String
)