package com.android.opus.ui.screen.filter

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.opus.R
import com.android.opus.common.ActivityFieldAdapter
import com.android.opus.common.ChosenSkillsAdapter
import com.android.opus.common.SkillsAdapter
import com.android.opus.domain.ActivityFieldInteractor
import com.android.opus.domain.SkillsInteractor
import com.android.opus.model.FieldOfActivity
import com.android.opus.model.Skill
import com.beloo.widget.chipslayoutmanager.ChipsLayoutManager
import com.beloo.widget.chipslayoutmanager.SpacingItemDecoration
import kotlinx.android.synthetic.main.fragment_filter.*
import kotlinx.coroutines.Dispatchers

class FilterScreenFragment : Fragment(R.layout.fragment_filter) {

    private var viewModel: FilterScreenViewModel = FilterScreenViewModel(
        activityFieldInteractor = ActivityFieldInteractor(Dispatchers.Default),
        skillsInteractor = SkillsInteractor(Dispatchers.Default)
    )

    private val activityFieldAdapter = ActivityFieldAdapter()
    private val skillsAdapter = SkillsAdapter { id ->
        viewModel.addSkillToChosen(id)

    }
    private val chosenSkillAdapter = ChosenSkillsAdapter { id ->
        viewModel.replaceSkillFromChosen(id)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.loadActivityFieldList()
        viewModel.loadSkills()

        setUpActivityFieldAdapter()
        setUpLoadedSkillsAdapter()
        setUpChosenSkillsAdapter()

        viewModel.activityFields.observe(this.viewLifecycleOwner, this::updateActivityFieldAdapter)
        viewModel.chosenSkills.observe(this.viewLifecycleOwner, this::updateChosenSkillsAdapter)
        viewModel.loadedSkills.observe(this.viewLifecycleOwner, this::updateLoadedSkillsAdapter)
    }

    private fun setUpActivityFieldAdapter() {
        activity_fields?.layoutManager = LinearLayoutManager(requireContext())
        activity_fields?.adapter = activityFieldAdapter
    }

    private fun setUpLoadedSkillsAdapter() {
        skills?.layoutManager = ChipsLayoutManager.newBuilder(requireContext())
            .build()
        skills?.adapter = skillsAdapter
        skills?.addItemDecoration(SpacingItemDecoration(8, 2))
    }

    private fun setUpChosenSkillsAdapter() {
        chosen_skills?.layoutManager = ChipsLayoutManager.newBuilder(requireContext())
            .build()
        chosen_skills?.adapter = chosenSkillAdapter
        chosen_skills?.addItemDecoration(SpacingItemDecoration(8, 2))
    }

    private fun updateActivityFieldAdapter(activityFields: List<FieldOfActivity>?) {
        activityFieldAdapter.submitList(activityFields)
    }

    private fun updateChosenSkillsAdapter(chosenSkills: List<Skill>?) {
        Log.d("Skills", skills.toString())
        chosenSkillAdapter.submitList(chosenSkills)
    }

    private fun updateLoadedSkillsAdapter(skills: List<Skill>?) {
        skillsAdapter.submitList(skills)
    }

    companion object {
        fun newInstance(): FilterScreenFragment = FilterScreenFragment()
    }
}
