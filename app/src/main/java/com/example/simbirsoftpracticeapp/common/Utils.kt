package com.example.simbirsoftpracticeapp.common

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.os.Parcelable
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.simbirsoftpracticeapp.R
import org.threeten.bp.Instant
import org.threeten.bp.Month
import org.threeten.bp.ZoneId
import org.threeten.bp.ZonedDateTime
import java.io.Serializable

object Utils {

    fun getFormatedDate(date: String): String {
        val now = ZonedDateTime.ofInstant(Instant.now(), ZoneId.systemDefault())
        val eventDate = ZonedDateTime.parse(date)
        if (eventDate.dayOfYear - now.dayOfYear < 30) {
            val month =
                if (eventDate.monthValue < 10) "0${eventDate.monthValue}" else eventDate.monthValue
            return "Осталось ${eventDate.dayOfYear - now.dayOfYear} дней (${eventDate.dayOfMonth}.${month})"
        } else {
            val month = when (eventDate.month) {
                Month.JANUARY -> "Январь"
                Month.FEBRUARY -> "Февраль"
                Month.MARCH -> "Март"
                Month.APRIL -> "Апрель"
                Month.MAY -> "Май"
                Month.JUNE -> "Июнь"
                Month.JULY -> "Июль"
                Month.AUGUST -> "Август"
                Month.SEPTEMBER -> "Сентябрь"
                Month.OCTOBER -> "Октябрь"
                Month.NOVEMBER -> "Ноябрь"
                else -> ""
            }
            return "$month ${eventDate.dayOfMonth}, ${eventDate.year}"
        }
    }

    fun loadImage(view: ImageView, url: String) {
        Glide.with(view.context)
            .load(url)
            .placeholder(ColorDrawable(Color.LTGRAY))
            .error(R.drawable.ic_round_icon_placeholder)
            .diskCacheStrategy(DiskCacheStrategy.NONE)
            .skipMemoryCache(true)
            .into(view)
    }

    @Suppress("DEPRECATION")
    inline fun <reified T : Serializable> Bundle.customGetSerializable(key: String): T? {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            getSerializable(key, T::class.java)
        } else {
            getSerializable(key) as? T
        }
    }

    @Suppress("DEPRECATION")
    inline fun <reified T : Parcelable> Bundle.customGetParcelable(key: String): T? {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            getParcelable(key, T::class.java)
        } else {
            getParcelable(key) as? T
        }
    }
}