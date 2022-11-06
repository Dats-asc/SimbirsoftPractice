package com.example.simbirsoftpracticeapp.presentation.help

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.simbirsoftpracticeapp.databinding.ItemCategoryBinding

class CategoryAdapter(
    private val categories: List<Category>,
    private val onListItemClick: (Int) -> Unit
) : RecyclerView.Adapter<CategoryAdapter.CategoryHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CategoryHolder = CategoryHolder.create(parent, onListItemClick)

    override fun onBindViewHolder(holder: CategoryHolder, position: Int) {
        holder.bind(categories[position])
    }

    override fun getItemCount(): Int = categories.size

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
}