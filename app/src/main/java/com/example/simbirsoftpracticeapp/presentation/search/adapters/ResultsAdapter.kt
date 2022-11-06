package com.example.simbirsoftpracticeapp.presentation.search.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.simbirsoftpracticeapp.databinding.ItemSearchResultBinding
import com.example.simbirsoftpracticeapp.presentation.search.SearchResult

class ResultsAdapter(
    private val results: List<SearchResult>,
    private val onListItemClick: (Int) -> Unit
) : RecyclerView.Adapter<ResultsAdapter.ResultsHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ResultsHolder = ResultsHolder.create(parent, onListItemClick)

    override fun onBindViewHolder(holder: ResultsHolder, position: Int) {
        holder.bind(results[position])
    }

    override fun getItemCount(): Int = results.size

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
}