package com.android.opus.ui.screen.resumeinfo

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.opus.R
import com.android.opus.common.CommonSkillsAdapter
import com.android.opus.domain.ResumeMainInfoInteractor
import com.android.opus.model.Resume
import com.android.opus.model.Skill
import com.android.opus.model.WorkPlace
import com.beloo.widget.chipslayoutmanager.ChipsLayoutManager
import com.beloo.widget.chipslayoutmanager.SpacingItemDecoration
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.fragment_resume_info.*
import kotlinx.coroutines.Dispatchers


class ResumeInfoFragment : Fragment(R.layout.fragment_resume_info) {

    private val viewModel = ResumeInfoViewModel(
        ResumeMainInfoInteractor(dispatcher = Dispatchers.Default)
    )

    private val skillsAdapter = CommonSkillsAdapter()
    private val workPlaceAdapter = WorkPlaceAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpSkillsAdapter()
        setUpWorkPlaceAdapter()

        viewModel.resume.observe(this.viewLifecycleOwner, this::updateResume)
    }

    private fun setUpSkillsAdapter() {
        skills?.layoutManager = ChipsLayoutManager.newBuilder(requireContext())
            .setScrollingEnabled(false)
            .setMaxViewsInRow(4)
            .setOrientation(ChipsLayoutManager.HORIZONTAL)
            .build()
        skills?.addItemDecoration(SpacingItemDecoration(16, 2))
        skills?.adapter = skillsAdapter
    }

    private fun setUpWorkPlaceAdapter() {
        work_experience?.layoutManager = LinearLayoutManager(requireContext())
        work_experience?.adapter = workPlaceAdapter
        work_experience.isNestedScrollingEnabled = false
    }

    private fun updateResume(resume: Resume) {
        updateResumeMainInfo(resume)
        updateSkillAdapter(resume.skills)
        updateWorkExperienceAdapter(resume.workExperience)
    }

    private fun updateResumeMainInfo(resume: Resume) {
        Glide.with(this)
            .load(resume.imageUrl)
            .into(photo)
        last_visit.text = resume.lastVisit
        user_name.text = resume.username
        min_salary.text = resume.minSalary
        age.text = resume.age
        experience.text = resume.experience
        level.text = resume.level
        status.text = resume.status
        self_description.text = resume.selfDescription
    }

    private fun updateSkillAdapter(skills: List<Skill>?) {
        skillsAdapter.submitList(skills)
    }

    private fun updateWorkExperienceAdapter(workExperience: List<WorkPlace>?) {
        workPlaceAdapter.submitList(workExperience)
    }

    companion object {
        fun newInstance(): ResumeInfoFragment = ResumeInfoFragment()
    }
}
