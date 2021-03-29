package com.android.opus.common

import androidx.recyclerview.widget.DiffUtil

class DisplayableItemsDiffUtilCallback(
    private val oldItems: List<DisplayableItem>,
    private val newItems: List<DisplayableItem>
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int {
        return oldItems.size
    }

    override fun getNewListSize(): Int {
        return newItems.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldItems[oldItemPosition].id() == newItems[newItemPosition].id()
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldItems[oldItemPosition].content() == newItems[newItemPosition].content()
    }
}
