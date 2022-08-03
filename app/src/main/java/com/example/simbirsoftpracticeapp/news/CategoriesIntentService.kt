package com.example.simbirsoftpracticeapp.news

import android.app.IntentService
import android.content.Intent
import android.util.Log
import com.example.simbirsoftpracticeapp.Constants
import com.example.simbirsoftpracticeapp.Utils
import com.example.simbirsoftpracticeapp.news.data.FilterCategories

class CategoriesIntentService : IntentService("") {
    override fun onHandleIntent(p0: Intent?) {
        var categories: FilterCategories? = null
        try {
            categories = Utils.getCategories(this.applicationContext)
        } catch (e: Exception) {
            Log.e("", e.message.orEmpty())
        }
        val responseIntent = Intent().apply {
            action = Constants.ACTION_SEND_CATEGORIES
            addCategory(Intent.CATEGORY_DEFAULT)
            putExtra(Constants.EXTRA_CATEGORIES, categories)
        }
        sendBroadcast(responseIntent)
    }
}