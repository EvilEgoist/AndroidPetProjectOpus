package com.android.opus.ui.screen.resume

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.android.opus.R
import com.android.opus.common.CommonSkillsAdapter
import com.android.opus.common.DisplayableItem
import com.android.opus.common.DisplayableItemsDiffUtilCallback
import com.android.opus.model.Resume
import com.beloo.widget.chipslayoutmanager.ChipsLayoutManager
import com.beloo.widget.chipslayoutmanager.SpacingItemDecoration
import com.bumptech.glide.Glide
import com.hannesdorfmann.adapterdelegates4.AbsListItemAdapterDelegate
import com.hannesdorfmann.adapterdelegates4.ListDelegationAdapter
import kotlinx.android.synthetic.main.item_resume.view.*

class ResumeDelegateAdapter(
    onResumeAction: (Int) -> Unit,
) : ListDelegationAdapter<List<DisplayableItem>>() {

    init {
        delegatesManager.addDelegate(ResumeDelegate(onResumeAction))
    }

    fun setData(items: List<DisplayableItem>) {
        val diffResult =
            DiffUtil.calculateDiff(DisplayableItemsDiffUtilCallback(this.items.orEmpty(), items))
        this.items = items
        diffResult.dispatchUpdatesTo(this)
    }
}

private class ResumeDelegate(
    private val onVacancyAction: (Int) -> Unit
) : AbsListItemAdapterDelegate<Resume, DisplayableItem, ResumeDelegate.ViewHolder>() {

    override fun isForViewType(
        item: DisplayableItem,
        items: List<DisplayableItem>,
        position: Int
    ): Boolean {
        return item is Resume
    }

    override fun onCreateViewHolder(parent: ViewGroup) = ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_resume, parent, false)
    )

    override fun onBindViewHolder(item: Resume, viewHolder: ViewHolder, payloads: List<Any>) {
        with(viewHolder.itemView) {
            Glide.with(context)
                .load(item.imageUrl)
                .into(resume_photo)

            job_position.text = item.jobPosition
            employee_name.text = item.employeeName
            experience.text = item.experience
            salary.text = item.salary
            level.text = item.level
            self_description.text = item.description

            skills.adapter = CommonSkillsAdapter().apply {
                submitList(item.skills)
            }
            watch_more.setOnClickListener { onVacancyAction.invoke(item.id) }
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        init {
            with(itemView) {
                skills.layoutManager = ChipsLayoutManager.newBuilder(itemView.context)
                    .setScrollingEnabled(false)
                    .setOrientation(ChipsLayoutManager.HORIZONTAL)
                    .setRowStrategy(ChipsLayoutManager.STRATEGY_DEFAULT)
                    .build()
                skills.addItemDecoration(SpacingItemDecoration(8, 2))
            }
        }
    }
}

