package com.example.simbirsoftpracticeapp.domain.entity

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class FilterCategory(
    @SerializedName("id")
    val id: Int,
    @SerializedName("title")
    val title: String,
    @SerializedName("is_checked")
    var isChecked: Boolean
) : Serializable
