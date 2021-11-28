package com.android.opus.ui.screen.company_profile

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.opus.R
import com.android.opus.common.adapters.skills.CommonSkillsAdapter
import com.android.opus.common.DisplayableItem
import com.android.opus.common.adapters.diff.DisplayableItemsDiffUtilCallback
import com.android.opus.model.ExpandedVacancy
import com.android.opus.ui.screen.expanded_vacancy.ExpandedVacancyAdapter
import com.beloo.widget.chipslayoutmanager.ChipsLayoutManager
import com.beloo.widget.chipslayoutmanager.SpacingItemDecoration
import com.hannesdorfmann.adapterdelegates4.AbsListItemAdapterDelegate
import com.hannesdorfmann.adapterdelegates4.ListDelegationAdapter
import kotlinx.android.synthetic.main.item_company_profile.view.*
import kotlinx.android.synthetic.main.item_company_profile.view.company_description
import kotlinx.android.synthetic.main.item_company_profile.view.location

class CompanyProfileAdapter() : ListDelegationAdapter<List<DisplayableItem>>() {

    init {
        delegatesManager.addDelegate(CompanyProfileDelegate())
    }

    fun setData(items: List<DisplayableItem>) {
        val diffResult =
            DiffUtil.calculateDiff(DisplayableItemsDiffUtilCallback(this.items.orEmpty(), items))
        this.items = items
        diffResult.dispatchUpdatesTo(this)
    }
}

private class CompanyProfileDelegate() : AbsListItemAdapterDelegate<ExpandedVacancy, DisplayableItem, CompanyProfileDelegate.ViewHolder>() {

    override fun isForViewType(
        item: DisplayableItem,
        items: List<DisplayableItem>,
        position: Int
    ): Boolean {
        return item is ExpandedVacancy
    }

    override fun onCreateViewHolder(parent: ViewGroup) = ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_company_profile, parent, false)
    )

    override fun onBindViewHolder(item: ExpandedVacancy, viewHolder: ViewHolder, payloads: List<Any>) {
        with(viewHolder.itemView) {
            vacancyName.text = item.titleVacancy
            titleEmployer.text = item.employerName
            profileSalary.text = item.salary
            description.text = item.description
            location.text = item.location
            typeOfEmployment.text = item.typeOfEmployment
            company_description.text = item.companyDescr
            reqSkills.adapter = CommonSkillsAdapter().apply {
                submitList(item.skills)
            }
            responsibilities.adapter = ExpandedVacancyAdapter().apply {
                submitList(item.responsibilities)
            }
            requirements.adapter = ExpandedVacancyAdapter().apply {
                submitList(item.requirements)
            }
            conditions.adapter = ExpandedVacancyAdapter().apply {
                submitList(item.conditions)
            }

            watch.setOnClickListener {
                expanded_vacancy_description.visibility = View.VISIBLE
                watch.visibility = View.GONE
            }
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        init {
            with(itemView) {
                reqSkills.layoutManager = ChipsLayoutManager.newBuilder(itemView.context)
                    .setScrollingEnabled(false)
                    .setOrientation(ChipsLayoutManager.HORIZONTAL)
                    .setRowStrategy(ChipsLayoutManager.STRATEGY_DEFAULT)
                    .build()
                reqSkills.addItemDecoration(SpacingItemDecoration(8, 2))
                responsibilities?.layoutManager = LinearLayoutManager(context)
                conditions?.layoutManager = LinearLayoutManager(context)
                requirements?.layoutManager = LinearLayoutManager(context)
            }
        }
    }
}
