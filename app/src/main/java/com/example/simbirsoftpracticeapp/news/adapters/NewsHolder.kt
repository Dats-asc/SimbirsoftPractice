package com.example.simbirsoftpracticeapp.news.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.simbirsoftpracticeapp.Utils
import com.example.simbirsoftpracticeapp.databinding.ItemEventCardBinding
import com.example.simbirsoftpracticeapp.news.data.CharityEvent
import com.jakewharton.threetenabp.AndroidThreeTen
import org.threeten.bp.Instant
import org.threeten.bp.Month
import org.threeten.bp.ZoneId
import org.threeten.bp.ZonedDateTime

class NewsHolder(
    private val binding: ItemEventCardBinding,
    private val onItemClick: (Int) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(event: CharityEvent) {
        with(binding) {
            tvEventTitle.text = event.title
            tvEventDescription.text = event.description
            btnDate.text = Utils.getFormatedDate(event.date)
        }

        itemView.setOnClickListener { onItemClick(event.id) }
    }

    companion object {
        fun create(
            parent: ViewGroup,
            action: (Int) -> Unit
        ) = NewsHolder(
            ItemEventCardBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ), action
        )
    }
}