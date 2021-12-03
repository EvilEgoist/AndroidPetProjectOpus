package com.android.opus.ui.screen.resumeinfo

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.opus.R
import com.android.opus.common.CommonSkillsAdapter
import com.android.opus.common.WorkPlaceAdapter
import com.android.opus.domain.ResumeMainInfoInteractor
import com.android.opus.model.ResumeInfo
import com.android.opus.model.Skill
import com.android.opus.model.WorkPlace
import com.beloo.widget.chipslayoutmanager.ChipsLayoutManager
import com.beloo.widget.chipslayoutmanager.SpacingItemDecoration
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
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

        fab_mail.setOnClickListener {
            viewModel.loadEmail()
        }

        viewModel.resume.observe(this.viewLifecycleOwner, this::updateResumeInfo)
        viewModel.email.observe(this.viewLifecycleOwner, this::updateEmail)
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

    private fun updateResumeInfo(resumeInfo: ResumeInfo) {
        updateResumeMainInfo(resumeInfo)
        updateSkillAdapter(resumeInfo.skills)
        updateWorkExperienceAdapter(resumeInfo.workExperience)
    }

    private fun updateResumeMainInfo(resumeInfo: ResumeInfo) {
        Glide.with(this)
            .load(resumeInfo.imageUrl)
            .into(photo_resume)
        last_visit.text = resumeInfo.lastVisit
        user_name.text = resumeInfo.username
        min_salary.text = resumeInfo.minSalary
        age.text = resumeInfo.age
        experience.text = resumeInfo.experience
        level.text = resumeInfo.level
        status.text = resumeInfo.status
        self_description.text = resumeInfo.selfDescription
    }

    private fun updateSkillAdapter(skills: List<Skill>?) {
        skillsAdapter.submitList(skills)
    }

    private fun updateWorkExperienceAdapter(workExperience: List<WorkPlace>?) {
        workPlaceAdapter.submitList(workExperience)
    }

    private fun updateEmail(email: String) {
        val snackBar = Snackbar.make(
            requireView().findViewById(R.id.coordinator),
            email,
            Snackbar.LENGTH_INDEFINITE
        )
        snackBar.setAction(getString(R.string.copy)) {
            val clipboard = context?.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clipData = ClipData.newPlainText(LABEL_TITLE, email)
            clipboard.setPrimaryClip(clipData)
        }
        snackBar.show()
    }

    companion object {
        private const val LABEL_TITLE = "text"
        fun newInstance(): ResumeInfoFragment = ResumeInfoFragment()
    }
}
