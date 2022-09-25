package com.example.simbirsoftpracticeapp.presentation.search.adapters

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.simbirsoftpracticeapp.presentation.news.adapters.EventsDiffUtils
import com.example.simbirsoftpracticeapp.domain.entity.CharityEvent

class EventsAdapter(
    private var events: List<CharityEvent>,
    private val onListItemClick: (Int) -> Unit
) : RecyclerView.Adapter<EventsHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): EventsHolder = EventsHolder.create(parent, onListItemClick)

    override fun onBindViewHolder(holder: EventsHolder, position: Int) {
        holder.bind(events[position])
    }

    fun updateEvents(newData: List<CharityEvent>) {
        val callback = EventsDiffUtils(events, newData)
        DiffUtil.calculateDiff(callback).dispatchUpdatesTo(this)
        events = newData
    }

    override fun getItemCount(): Int = events.size
}