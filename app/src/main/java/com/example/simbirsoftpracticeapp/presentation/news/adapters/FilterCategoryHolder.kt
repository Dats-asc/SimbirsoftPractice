package com.example.simbirsoftpracticeapp.presentation.news.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.CompoundButton
import androidx.recyclerview.widget.RecyclerView
import com.example.simbirsoftpracticeapp.databinding.ItemFilterCategoryBinding
import com.example.simbirsoftpracticeapp.domain.entity.FilterCategory

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