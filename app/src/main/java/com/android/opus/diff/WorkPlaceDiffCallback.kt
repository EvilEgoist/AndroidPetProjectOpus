package com.android.opus.diff

import androidx.recyclerview.widget.DiffUtil
import com.android.opus.model.WorkPlace

class WorkPlaceDiffCallback : DiffUtil.ItemCallback<WorkPlace>() {
    override fun areItemsTheSame(oldItem: WorkPlace, newItem: WorkPlace): Boolean =
        oldItem.id() == newItem.id()

    override fun areContentsTheSame(oldItem: WorkPlace, newItem: WorkPlace): Boolean =
        oldItem.content() == newItem.content()
}
