package com.example.simbirsoftpracticeapp.presentation.news.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.simbirsoftpracticeapp.common.Utils
import com.example.simbirsoftpracticeapp.databinding.ItemEventCardBinding
import com.example.simbirsoftpracticeapp.domain.entity.CharityEvent

class NewsAdapter(
    private var events: List<CharityEvent>,
    private val onItemClick: (Int) -> Unit
) : RecyclerView.Adapter<NewsAdapter.NewsHolder>() {

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
}