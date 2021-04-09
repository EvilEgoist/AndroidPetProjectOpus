package com.android.opus.common

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.android.opus.diff.CommonSkillDiffCallback
import com.android.opus.R
import com.android.opus.model.Skill
import kotlinx.android.synthetic.main.item_skill.view.*

class CommonSkillsAdapter :
    ListAdapter<Skill, CommonSkillsAdapter.CommonSkillViewHolder>(
        CommonSkillDiffCallback()
    ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommonSkillViewHolder =
        CommonSkillViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_skill, parent, false)
        )

    override fun onBindViewHolder(holder: CommonSkillViewHolder, position: Int) {
        holder.itemView.skill.text = getItem(position).title
    }

    class CommonSkillViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}
