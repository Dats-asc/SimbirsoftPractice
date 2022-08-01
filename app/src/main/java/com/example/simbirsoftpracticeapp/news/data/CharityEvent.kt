package com.example.simbirsoftpracticeapp.news.data

import com.google.gson.annotations.SerializedName

data class CharityEvent(
    @SerializedName("id")
    val id: Int,
    @SerializedName("title")
    val title: String,
    @SerializedName("date")
    val date: String,
    @SerializedName("charitable_foundation")
    val charitableFoundation: String,
    @SerializedName("address")
    val address: String,
    @SerializedName("phone_numbers")
    val phoneNumbers: List<String>,
    @SerializedName("img_1")
    val img1: String,
    @SerializedName("img_2")
    val img2: String,
    @SerializedName("img_3")
    val img3: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("members")
    val members: List<EventMember>,
    @SerializedName("members_count")
    val membersCount: Int,
    @SerializedName("source_url")
    val sourceUrl: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("category_id")
    val categoryId: Int
)
