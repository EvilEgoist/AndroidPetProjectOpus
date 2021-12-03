package com.android.opus.ui.screen.expanded_vacancy

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.android.opus.R
import com.android.opus.model.ShortDescription
import kotlinx.android.synthetic.main.item_expanded_vacancy_description.view.*

class ExpandedVacancyAdapter:
        ListAdapter<ShortDescription, ExpandedVacancyAdapter.ExpandedVacancyViewHolder>(
        ExpandedVacancyDiffCallback()
){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExpandedVacancyViewHolder =
            ExpandedVacancyViewHolder(
                    LayoutInflater.from(parent.context).inflate(R.layout.item_expanded_vacancy_description, parent, false)
            )

    override fun onBindViewHolder(holder: ExpandedVacancyViewHolder, position: Int) {
        holder.itemView.expanded_vacancy_item.text = getItem(position).content
    }

    class ExpandedVacancyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    private class ExpandedVacancyDiffCallback : DiffUtil.ItemCallback<ShortDescription>() {
        override fun areItemsTheSame(oldItem: ShortDescription, newItem: ShortDescription): Boolean =
                oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: ShortDescription, newItem: ShortDescription): Boolean =
                oldItem == newItem
    }
}
