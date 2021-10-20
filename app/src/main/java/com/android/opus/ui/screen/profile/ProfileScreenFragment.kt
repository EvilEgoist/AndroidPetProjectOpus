package com.android.opus.ui.screen.profile

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.opus.R
import com.android.opus.common.CommonSkillsAdapter
import com.android.opus.domain.ProfileInteractor
import com.android.opus.model.Profile
import com.android.opus.model.Skill
import com.android.opus.model.WorkPlace
import com.android.opus.common.WorkPlaceAdapter
import com.beloo.widget.chipslayoutmanager.ChipsLayoutManager
import com.beloo.widget.chipslayoutmanager.SpacingItemDecoration
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.fragment_profile.*
import kotlinx.android.synthetic.main.fragment_resume_info.age
import kotlinx.android.synthetic.main.fragment_resume_info.experience
import kotlinx.android.synthetic.main.fragment_resume_info.level
import kotlinx.android.synthetic.main.fragment_resume_info.self_description
import kotlinx.android.synthetic.main.fragment_resume_info.skills
import kotlinx.android.synthetic.main.fragment_resume_info.status
import kotlinx.android.synthetic.main.fragment_resume_info.user_name
import kotlinx.android.synthetic.main.fragment_resume_info.work_experience
import kotlinx.coroutines.Dispatchers

class ProfileScreenFragment : Fragment(R.layout.fragment_profile) {

    private val viewModel = ProfileScreenViewModel(
        ProfileInteractor(dispatcher = Dispatchers.Default)
    )

    private val skillsAdapter = CommonSkillsAdapter()
    private val workPlaceAdapter = WorkPlaceAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpSkillsAdapter()
        setUpWorkPlaceAdapter()

        viewModel.resume.observe(this.viewLifecycleOwner, this::updateProfile)
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

    private fun updateProfile(profile: Profile) {
        updateProfileMainInfo(profile)
        updateSkillAdapter(profile.skills)
        updateWorkExperienceAdapter(profile.workExperience)
    }

    private fun updateProfileMainInfo(profile: Profile) {
        Glide.with(this)
            .load(profile.imageUrl)
            .into(photo_profile)
        user_name.text = profile.username
        age.text = profile.age
        experience.text = profile.experience
        level.text = profile.level
        status.text = profile.status
        self_description.text = profile.selfDescription
    }

    private fun updateSkillAdapter(skills: List<Skill>?) {
        skillsAdapter.submitList(skills)
    }

    private fun updateWorkExperienceAdapter(workExperience: List<WorkPlace>?) {
        workPlaceAdapter.submitList(workExperience)
    }

    companion object {
        fun newInstance(): ProfileScreenFragment = ProfileScreenFragment()
    }
}
