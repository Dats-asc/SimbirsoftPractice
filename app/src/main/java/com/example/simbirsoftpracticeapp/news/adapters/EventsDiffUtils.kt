package com.example.simbirsoftpracticeapp.news.adapters

import androidx.recyclerview.widget.DiffUtil
import com.example.simbirsoftpracticeapp.news.data.CharityEvent

class EventsDiffUtils(
    private val oldList: List<CharityEvent>,
    private val newList: List<CharityEvent>
) : DiffUtil.Callback() {

    override fun getOldListSize() = oldList.size

    override fun getNewListSize() = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].id == newList[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].categoryId == newList[newItemPosition].categoryId
    }
}