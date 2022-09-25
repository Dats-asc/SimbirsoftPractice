package com.example.simbirsoftpracticeapp.presentation.search.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.simbirsoftpracticeapp.databinding.ItemSearchResultBinding
import com.example.simbirsoftpracticeapp.domain.entity.CharityEvent

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