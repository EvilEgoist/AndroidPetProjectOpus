package com.android.opus.diff

import androidx.recyclerview.widget.DiffUtil
import com.android.opus.model.Skill

class CommonSkillDiffCallback : DiffUtil.ItemCallback<Skill>() {
    override fun areItemsTheSame(oldItem: Skill, newItem: Skill): Boolean =
        oldItem.id() == newItem.id()

    override fun areContentsTheSame(oldItem: Skill, newItem: Skill): Boolean =
        oldItem.content() == newItem.content()
}
