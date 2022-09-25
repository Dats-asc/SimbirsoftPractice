package com.example.simbirsoftpracticeapp.data.entity

import com.google.gson.annotations.SerializedName

data class EventsResponseItem(
    @SerializedName("address")
    val address: String,
    @SerializedName("category_id")
    val categoryId: Int,
    @SerializedName("charitable_foundation")
    val charitableFoundation: String,
    @SerializedName("date")
    val date: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("img_1")
    val img1: String,
    @SerializedName("img_2")
    val img2: String,
    @SerializedName("img_3")
    val img3: String,
    @SerializedName("members")
    val members: List<Member>,
    @SerializedName("members_count")
    val membersCount: Int,
    @SerializedName("phone_numbers")
    val phoneNumbers: List<String>,
    @SerializedName("source_url")
    val sourceUrl: String,
    @SerializedName("title")
    val title: String
)