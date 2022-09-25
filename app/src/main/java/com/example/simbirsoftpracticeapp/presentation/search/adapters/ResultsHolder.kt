package com.example.simbirsoftpracticeapp.presentation.search.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.simbirsoftpracticeapp.databinding.ItemSearchResultBinding
import com.example.simbirsoftpracticeapp.presentation.search.SearchResult

class ResultsHolder(
    private val binding: ItemSearchResultBinding,
    private val onListItemClick: (Int) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: SearchResult) {
        binding.tvEventTitle.text = item.resultTitle
        binding.tvEventTitle.isSelected = true

        itemView.setOnClickListener { onListItemClick(item.id) }
    }

    companion object {
        fun create(
            parent: ViewGroup,
            action: (Int) -> Unit
        ) = ResultsHolder(
            ItemSearchResultBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ), action
        )
    }
}