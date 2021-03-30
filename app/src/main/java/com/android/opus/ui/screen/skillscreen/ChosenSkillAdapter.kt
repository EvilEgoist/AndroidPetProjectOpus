package com.android.opus.ui.screen.skillscreen

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.android.opus.R
import com.android.opus.model.SkillsScreenField
import kotlinx.android.synthetic.main.item_chosen_skill.view.*

class ChosenSkillAdapter(
        private val onClickListener: (Int) -> Unit
) :ListAdapter<SkillsScreenField, ChosenSkillAdapter.ChosenSkillViewHolder>(
        ChosenSkillDiffCallback()
) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChosenSkillViewHolder =
            ChosenSkillViewHolder(
                    LayoutInflater.from(parent.context).inflate(R.layout.item_chosen_skill, parent, false)
            )

    override fun onBindViewHolder(holder: ChosenSkillViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ChosenSkillViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: SkillsScreenField) {
            itemView.item_chosen_skill.text = item.title
            itemView.item_chosen_skill.setOnClickListener {onClickListener.invoke(item.id) }
        }
    }
}

private class ChosenSkillDiffCallback : DiffUtil.ItemCallback<SkillsScreenField>() {
    override fun areItemsTheSame(oldItem: SkillsScreenField, newItem: SkillsScreenField): Boolean =
            oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: SkillsScreenField, newItem: SkillsScreenField): Boolean =
            (oldItem == newItem)
}