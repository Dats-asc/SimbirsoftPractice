package com.example.simbirsoftpracticeapp.data.database.events

import com.google.gson.annotations.SerializedName

data class PhoneNumbersList(
    @SerializedName("phone_numbers")
    val list: List<String>
)