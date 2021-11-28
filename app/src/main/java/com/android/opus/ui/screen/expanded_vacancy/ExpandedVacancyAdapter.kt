package com.android.opus.ui.screen.expanded_vacancy

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.android.opus.R
import kotlinx.android.synthetic.main.expanded_vacancy_descr_item.view.*

class ExpandedVacancyAdapter:
        ListAdapter<String, ExpandedVacancyAdapter.ExpandedVacancyViewHolder>(
        ExpandedVacancyDiffCallback()
){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExpandedVacancyViewHolder =
            ExpandedVacancyViewHolder(
                    LayoutInflater.from(parent.context).inflate(R.layout.expanded_vacancy_descr_item, parent, false)
            )

    override fun onBindViewHolder(holder: ExpandedVacancyViewHolder, position: Int) {
        holder.itemView.expanded_vacancy_item.text = getItem(position)
    }

    class ExpandedVacancyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    private class ExpandedVacancyDiffCallback : DiffUtil.ItemCallback<String>() {
        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean =
                oldItem == newItem

        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean =
                oldItem == newItem
    }
}
