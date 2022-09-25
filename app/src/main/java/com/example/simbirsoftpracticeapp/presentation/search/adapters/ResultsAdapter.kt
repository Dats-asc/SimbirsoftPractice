package com.example.simbirsoftpracticeapp.presentation.search.adapters

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.simbirsoftpracticeapp.presentation.search.SearchResult

class ResultsAdapter(
    private val results: List<SearchResult>,
    private val onListItemClick: (Int) -> Unit
) : RecyclerView.Adapter<ResultsHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ResultsHolder = ResultsHolder.create(parent, onListItemClick)

    override fun onBindViewHolder(holder: ResultsHolder, position: Int) {
        holder.bind(results[position])
    }

    override fun getItemCount(): Int = results.size
}