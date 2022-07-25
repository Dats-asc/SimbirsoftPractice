package com.example.simbirsoftpracticeapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.simbirsoftpracticeapp.databinding.ItemCategoryBinding

class CategoryHolder(
    private val binding: ItemCategoryBinding,
    private val onListItemClick: (Int) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: Category) {
        with(binding) {
            tvCategoryTitle.text = item.title
            ivCategoryLogo.setImageResource(item.imgResId)
        }

        itemView.setOnClickListener { onListItemClick(item.id) }
    }

    companion object {
        fun create(
            parent: ViewGroup,
            action: (Int) -> Unit
        ) = CategoryHolder(
            ItemCategoryBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ), action
        )
    }
}