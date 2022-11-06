package com.example.simbirsoftpracticeapp.presentation.search.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.simbirsoftpracticeapp.databinding.ItemSearchResultBinding
import com.example.simbirsoftpracticeapp.presentation.news.adapters.EventsDiffUtils
import com.example.simbirsoftpracticeapp.domain.entity.CharityEvent

class EventsAdapter(
    private var events: List<CharityEvent>,
    private val onListItemClick: (Int) -> Unit
) : RecyclerView.Adapter<EventsAdapter.EventsHolder>() {

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

    class EventsHolder(
        private val binding: ItemSearchResultBinding,
        private val onListItemClick: (Int) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: CharityEvent) {
            binding.tvEventTitle.text = item.title

            itemView.setOnClickListener { onListItemClick(item.id) }
        }

        companion object {
            fun create(
                parent: ViewGroup,
                action: (Int) -> Unit
            ) = EventsHolder(
                ItemSearchResultBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                ), action
            )
        }
    }
}