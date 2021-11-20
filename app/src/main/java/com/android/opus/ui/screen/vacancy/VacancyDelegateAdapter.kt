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
import coil.load
import coil.transform.CircleCropTransformation
import com.android.opus.R
import com.android.opus.common.DisplayableItem
import com.android.opus.common.adapters.diff.DisplayableItemsDiffUtilCallback
import com.android.opus.model.Vacancy
import com.google.android.material.chip.Chip
import com.hannesdorfmann.adapterdelegates4.AbsListItemAdapterDelegate
import com.hannesdorfmann.adapterdelegates4.ListDelegationAdapter
import kotlinx.android.synthetic.main.item_vacancy.view.*

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

    override fun isForViewType(item: DisplayableItem, items: List<DisplayableItem>, position: Int): Boolean {
        return item is Vacancy
    }

    override fun onCreateViewHolder(parent: ViewGroup) = ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_vacancy, parent, false)
    )

    override fun onBindViewHolder(item: Vacancy, viewHolder: ViewHolder, payloads: List<Any>) {
        viewHolder.bind(item, onVacancyAction)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: Vacancy, onVacancyAction: (Int) -> Unit) {
            itemView.titleVacancy.text = item.titleVacancy
            itemView.employerName.text = item.employerName
            itemView.salary.text = item.salary
            itemView.employerImage.load(item.imageUrl) {
                transformations(CircleCropTransformation())
            }
            itemView.vacancyDescription.text = item.description

            for ((index, skill) in item.skills.withIndex()) {
                if (index == 4) {
                    break
                }
                val view = Chip(itemView.context)
                view.text = skill.title
                view.id = View.generateViewId()
                view.isEnabled = false
                itemView.skillsGroup.addView(view)
            }

            itemView.setOnClickListener { onVacancyAction.invoke(item.id) }
        }
    }
}

