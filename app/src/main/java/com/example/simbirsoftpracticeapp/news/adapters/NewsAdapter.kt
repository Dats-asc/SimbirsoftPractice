package com.example.simbirsoftpracticeapp.news.adapters

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.simbirsoftpracticeapp.news.data.CharityEvent

class NewsAdapter(
    private var events: List<CharityEvent>,
    private val onItemClick: (Int) -> Unit
) : RecyclerView.Adapter<NewsHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): NewsHolder = NewsHolder.create(parent, onItemClick)

    override fun onBindViewHolder(holder: NewsHolder, position: Int) {
        holder.bind(events[position])
    }

    fun updateEvents(newData: List<CharityEvent>) {
        val callback = EventsDiffUtils(events, newData)
        DiffUtil.calculateDiff(callback).dispatchUpdatesTo(this)
        events = newData
    }

    override fun getItemCount(): Int = events.size
}