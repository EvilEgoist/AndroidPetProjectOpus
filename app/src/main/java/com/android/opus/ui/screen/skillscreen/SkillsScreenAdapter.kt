package com.android.opus.ui.screen.skillscreen

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.android.opus.R
import com.android.opus.model.FieldOfActivity
import com.android.opus.model.SkillsScreenField
import kotlinx.android.synthetic.main.item_activity_field.view.*
import kotlinx.android.synthetic.main.item_skills_screen.*
import kotlinx.android.synthetic.main.item_skills_screen.view.*

class SkillsScreenAdapter(
    private val onClickListener: (Int) -> Unit
) :ListAdapter<SkillsScreenField, SkillsScreenAdapter.SkillsScreenViewHolder>(
        SkillsScreenDiffCallback()
    ) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SkillsScreenViewHolder =
        SkillsScreenViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_skills_screen, parent, false)
        )

    override fun onBindViewHolder(holder: SkillsScreenViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

   inner class SkillsScreenViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: SkillsScreenField) {
            itemView.item_skills.text = item.title
            itemView.item_skills.setOnClickListener {onClickListener.invoke(item.id) }
        }
    }
}

private class SkillsScreenDiffCallback : DiffUtil.ItemCallback<SkillsScreenField>() {
    override fun areItemsTheSame(oldItem: SkillsScreenField, newItem: SkillsScreenField): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: SkillsScreenField, newItem: SkillsScreenField): Boolean =
        (oldItem == newItem)
}

