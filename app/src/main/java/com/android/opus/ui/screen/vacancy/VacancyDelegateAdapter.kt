package com.android.opus.ui.screen.vacancy

import android.app.ActionBar
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.view.marginEnd
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.android.opus.R
import com.android.opus.common.DisplayableItem
import com.android.opus.common.adapters.diff.DisplayableItemsDiffUtilCallback
import com.android.opus.common.adapters.skills.CommonSkillsAdapter
import com.android.opus.model.Vacancy
import com.beloo.widget.chipslayoutmanager.ChipsLayoutManager
import com.beloo.widget.chipslayoutmanager.SpacingItemDecoration
import com.bumptech.glide.Glide
import com.hannesdorfmann.adapterdelegates4.AbsListItemAdapterDelegate
import com.hannesdorfmann.adapterdelegates4.ListDelegationAdapter
import kotlinx.android.synthetic.main.item_vacancy.view.*
import kotlinx.android.synthetic.main.item_vacancy.view.salary

class VacancyDelegateAdapter(
    onVacancyAction: (Int) -> Unit,
) : ListDelegationAdapter<List<DisplayableItem>>() {

    init {
        delegatesManager.addDelegate(VacancyDelegate(onVacancyAction))
    }

    fun setData(items: List<DisplayableItem>) {
        val diffResult = DiffUtil.calculateDiff(
            DisplayableItemsDiffUtilCallback(
                this.items.orEmpty(),
                items
            )
        )
        this.items = items
        diffResult.dispatchUpdatesTo(this)
    }
}

private class VacancyDelegate(
    private val onVacancyAction: (Int) -> Unit
) : AbsListItemAdapterDelegate<Vacancy, DisplayableItem, VacancyDelegate.ViewHolder>() {

    val viewPool = RecyclerView.RecycledViewPool()

    override fun isForViewType(item: DisplayableItem, items: List<DisplayableItem>, position: Int): Boolean {
        return item is Vacancy
    }

    override fun onCreateViewHolder(parent: ViewGroup) = ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_vacancy, parent, false)
    )

    override fun onBindViewHolder(item: Vacancy, viewHolder: ViewHolder, payloads: List<Any>) {
        with(viewHolder.itemView){
            Glide.with(context)
                .load(item.imageUrl)
                .into(employer_image)
            title_vacancy.text = item.titleVacancy
            employer_name.text = item.employerName
            salary.text = item.salary
            vacancy_description.text = item.description
            skills.setRecycledViewPool(viewPool)
            skills.adapter = CommonSkillsAdapter().apply {
                submitList(item.skills)
            }
            watch_more_vacancy.setOnClickListener { onVacancyAction.invoke(item.id) }
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

