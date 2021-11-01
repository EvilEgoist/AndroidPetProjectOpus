package com.android.opus.common

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.android.opus.R
import com.android.opus.model.FieldOfActivity
import kotlinx.android.synthetic.main.item_activity_field.view.*

class ActivityFieldAdapter :
    ListAdapter<FieldOfActivity, ActivityFieldAdapter.ActivityFieldViewHolder>(
        ActivityFieldDiffCallback()
    ) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActivityFieldViewHolder =
        ActivityFieldViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_activity_field, parent, false)
        )

    override fun onBindViewHolder(holder: ActivityFieldViewHolder, position: Int) {
        with(holder.itemView) {
            field_of_activity.text = getItem(position).title
        }
    }

    class ActivityFieldViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}

private class ActivityFieldDiffCallback : DiffUtil.ItemCallback<FieldOfActivity>() {
    override fun areItemsTheSame(oldItem: FieldOfActivity, newItem: FieldOfActivity): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: FieldOfActivity, newItem: FieldOfActivity): Boolean =
        (oldItem == newItem)
}