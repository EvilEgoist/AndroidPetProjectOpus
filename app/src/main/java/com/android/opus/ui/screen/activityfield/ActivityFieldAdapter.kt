package com.android.opus.ui.screen.activityfield

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.android.opus.R
import kotlinx.android.synthetic.main.item_activity_field.view.*

class ActivityFieldAdapter : ListAdapter<String, ActivityFieldAdapter.ActivityFieldViewHolder>(ActivityFieldDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActivityFieldViewHolder =
        ActivityFieldViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_activity_field, parent, false)
        )

    override fun onBindViewHolder(holder: ActivityFieldViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
    class ActivityFieldViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(title: String) {
            itemView.fieldOfActivity.text = title
        }
    }
}

private class ActivityFieldDiffCallback : DiffUtil.ItemCallback<String>() {
    override fun areItemsTheSame(oldItem: String, newItem: String): Boolean =
        areContentsTheSame(oldItem, newItem)

    override fun areContentsTheSame(oldItem: String, newItem: String): Boolean =
        (oldItem == newItem)
}


