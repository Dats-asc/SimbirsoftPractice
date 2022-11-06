package com.example.simbirsoftpracticeapp.presentation.news.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.CompoundButton
import androidx.recyclerview.widget.RecyclerView
import com.example.simbirsoftpracticeapp.databinding.ItemFilterCategoryBinding
import com.example.simbirsoftpracticeapp.domain.entity.FilterCategory

class FilterCategoryAdapter(
    private val categories: List<FilterCategory>,
    private val onCategorySwitch: (Int, Boolean) -> Unit
) : RecyclerView.Adapter<FilterCategoryAdapter.FilterCategoryHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FilterCategoryHolder = FilterCategoryHolder.create(parent, onCategorySwitch)

    override fun onBindViewHolder(holder: FilterCategoryHolder, position: Int) {
        holder.bind(categories[position])
    }

    override fun getItemCount(): Int = categories.size

    class FilterCategoryHolder(
        private val binding: ItemFilterCategoryBinding,
        private val onCategorySwitch: (Int, Boolean) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: FilterCategory) {
            binding.tvFilterTitle.text = item.title
            binding.categorySwitch.isChecked = item.isChecked
            binding.categorySwitch.setOnCheckedChangeListener { _: CompoundButton, isChecked ->
                onCategorySwitch(item.id, isChecked)
            }
            itemView.setOnClickListener {
                binding.categorySwitch.isChecked = !binding.categorySwitch.isChecked
            }
        }

        companion object {
            fun create(
                parent: ViewGroup,
                action: (Int, Boolean) -> Unit
            ) = FilterCategoryHolder(
                ItemFilterCategoryBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                ), action
            )
        }
    }
}