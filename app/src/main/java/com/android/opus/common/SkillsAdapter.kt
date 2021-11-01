package com.android.opus.common

import android.view.LayoutInflater
import android.view.ViewGroup
import com.android.opus.R

class SkillsAdapter(onSkillAction: (Int) -> Unit) :
    BaseSkillsAdapter(onSkillAction) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseSkillViewHolder =
        BaseSkillViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_skill, parent, false)
        )
}
