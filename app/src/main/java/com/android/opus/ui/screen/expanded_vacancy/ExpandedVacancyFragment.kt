package com.android.opus.ui.screen.expanded_vacancy

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.opus.R
import com.android.opus.common.adapters.skills.CommonSkillsAdapter
import com.android.opus.domain.ExpandedVacancyInteractor
import com.android.opus.model.ExpandedVacancy
import com.android.opus.model.ShortDescription
import com.android.opus.model.Skill
import com.beloo.widget.chipslayoutmanager.ChipsLayoutManager
import com.beloo.widget.chipslayoutmanager.SpacingItemDecoration
import kotlinx.android.synthetic.main.fragment_expanded_vacancy.*
import kotlinx.coroutines.Dispatchers

class ExpandedVacancyFragment: Fragment(R.layout.fragment_expanded_vacancy) {

    private val viewModel = ExpandedVacancyViewModel(
        ExpandedVacancyInteractor(dispatcher = Dispatchers.Default)
    )

    private val skillsAdapter = CommonSkillsAdapter()
    private val responsibilitiesAdapter = ExpandedVacancyAdapter()
    private val requirementsAdapter = ExpandedVacancyAdapter()
    private val conditionsAdapter = ExpandedVacancyAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpSkillsAdapter()
        setUpResponsibilitiesAdapter()
        setUpRequirementsAdapter()
        setUpConditionsAdapter()
        viewModel.expandedVacancy.observe(this.viewLifecycleOwner, this::updateExpandedVacancy)
    }

    private fun setUpSkillsAdapter() {
        reqSkills?.layoutManager = ChipsLayoutManager.newBuilder(requireContext())
            .setScrollingEnabled(false)
            .setMaxViewsInRow(4)
            .setOrientation(ChipsLayoutManager.HORIZONTAL)
            .build()
        reqSkills?.addItemDecoration(SpacingItemDecoration(16, 2))
        reqSkills?.adapter = skillsAdapter
    }

    private fun setUpResponsibilitiesAdapter(){
        responsibilities?.layoutManager = LinearLayoutManager(requireContext())
        responsibilities?.adapter = responsibilitiesAdapter
        responsibilities?.isNestedScrollingEnabled = false
    }

    private fun setUpRequirementsAdapter(){
        requirements?.layoutManager = LinearLayoutManager(requireContext())
        requirements?.adapter = requirementsAdapter
        requirements?.isNestedScrollingEnabled = false
    }

    private fun setUpConditionsAdapter(){
        conditions?.layoutManager = LinearLayoutManager(requireContext())
        conditions?.adapter = conditionsAdapter
        conditions?.isNestedScrollingEnabled = false
    }

    private fun updateExpandedVacancy(expVacancy: ExpandedVacancy) {
        updateExpVacancyMainInfo(expVacancy)
        updateAllAdapters(expVacancy.skills, expVacancy.responsibilities, expVacancy.requirements, expVacancy.conditions)
    }

    private fun updateExpVacancyMainInfo(expVacancy: ExpandedVacancy) {
        vacancyTitle.text = expVacancy.titleVacancy
        titleEmployer.text = expVacancy.employerName
        salary.text = expVacancy.salary
        vacancyDescription.text = expVacancy.description
        location.text = expVacancy.location
        typeOfEmployment.text = expVacancy.typeOfEmployment
        company_description.text = expVacancy.companyDescr
    }

    private fun updateAllAdapters(skills: List<Skill>?, responsibilities: List<ShortDescription>, requirements: List<ShortDescription>, conditions: List<ShortDescription>) {
        skillsAdapter.submitList(skills)
        responsibilitiesAdapter.submitList(responsibilities)
        requirementsAdapter.submitList(requirements)
        conditionsAdapter.submitList(conditions)
    }

    companion object {
        fun newInstance(): ExpandedVacancyFragment = ExpandedVacancyFragment()
    }
}