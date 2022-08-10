package com.example.simbirsoftpracticeapp.news

import android.app.IntentService
import android.content.Intent
import android.util.Log
import com.example.simbirsoftpracticeapp.Constants
import com.example.simbirsoftpracticeapp.Utils
import com.example.simbirsoftpracticeapp.news.data.FilterCategories
import io.reactivex.rxjava3.schedulers.Schedulers

class CategoriesIntentService : IntentService("") {
    override fun onHandleIntent(p0: Intent?) {
        var categories: FilterCategories? = null
        try {
            Utils.getCategories(this.applicationContext)
                .map { it.categories }
                .subscribeOn(Schedulers.computation())
                .observeOn(Schedulers.newThread())
                .subscribe {
                    val responseIntent = Intent().apply {
                        action = Constants.ACTION_SEND_CATEGORIES
                        addCategory(Intent.CATEGORY_DEFAULT)
                        putExtra(Constants.EXTRA_CATEGORIES, categories)
                    }
                    sendBroadcast(responseIntent)
                }
        } catch (e: Exception) {
            Log.e("", e.message.orEmpty())
        }
    }
}