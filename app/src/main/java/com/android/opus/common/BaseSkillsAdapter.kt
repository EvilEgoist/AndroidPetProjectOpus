package com.android.opus.common

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.android.opus.R
import com.android.opus.model.Skill
import kotlinx.android.synthetic.main.item_skill.view.*

abstract class BaseSkillsAdapter(private val onClickListener: (Int) -> Unit) :
    ListAdapter<Skill, BaseSkillsAdapter.BaseSkillViewHolder>(
        SkillDiffCallback()
    ) {

    override fun onBindViewHolder(holder: BaseSkillViewHolder, position: Int) {
        val item = getItem(position)
        with(holder.itemView) {
            skill.text = item.title
            skill.setOnClickListener { onClickListener.invoke(item.id) }
        }
    }

    class BaseSkillViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}

private class SkillDiffCallback : DiffUtil.ItemCallback<Skill>() {
    override fun areItemsTheSame(oldItem: Skill, newItem: Skill): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Skill, newItem: Skill): Boolean =
        oldItem == newItem
}
