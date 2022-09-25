package com.example.simbirsoftpracticeapp.data.database.events

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "charity_events")
data class CharityEventEntity(
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "dat e") val date: String,
    @ColumnInfo(name = "charitable_foundation") val charitableFoundation: String,
    @ColumnInfo(name = "address") val address: String,
    @ColumnInfo(name = "phone_numbers") val phoneNumbers: String,
    @ColumnInfo(name = "img_1") val img1: String,
    @ColumnInfo(name = "img_2") val img2: String,
    @ColumnInfo(name = "img_3") val img3: String,
    @ColumnInfo(name = "description") val description: String,
    @ColumnInfo(name = "members") val members: String,
    @ColumnInfo(name = "members_count") val membersCount: Int,
    @ColumnInfo(name = "source_url") val sourceUrl: String,
    @ColumnInfo(name = "email") val email: String,
    @ColumnInfo(name = "category_id") val categoryId: Int
)