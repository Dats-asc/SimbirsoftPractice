package com.example.simbirsoftpracticeapp.news.adapters

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.simbirsoftpracticeapp.news.data.FilterCategory

class FilterCategoryAdapter(
    private val categories: List<FilterCategory>,
    private val onCategorySwitch: (Int, Boolean) -> Unit
) : RecyclerView.Adapter<FilterCategoryHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FilterCategoryHolder = FilterCategoryHolder.create(parent, onCategorySwitch)

    override fun onBindViewHolder(holder: FilterCategoryHolder, position: Int) {
        holder.bind(categories[position])
    }

    override fun getItemCount(): Int = categories.size
}