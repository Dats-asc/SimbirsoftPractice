package com.example.simbirsoftpracticeapp.presentation.news.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.simbirsoftpracticeapp.common.Utils
import com.example.simbirsoftpracticeapp.databinding.ItemEventCardBinding
import com.example.simbirsoftpracticeapp.domain.entity.CharityEvent

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