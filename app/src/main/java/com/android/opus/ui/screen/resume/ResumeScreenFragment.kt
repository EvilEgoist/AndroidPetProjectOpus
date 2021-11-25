package com.android.opus.ui.screen.resume

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.opus.R
import com.android.opus.domain.ResumeInteractor
import com.android.opus.model.Resume
import com.android.opus.utils.bindFloatingActionButton
import kotlinx.android.synthetic.main.fragment_vacanies.*
import kotlinx.coroutines.Dispatchers

class ResumeScreenFragment : Fragment(R.layout.fragment_resumes) {
    private var viewModel = ResumeScreenViewModel(
        ResumeInteractor(dispatcher = Dispatchers.IO)
    )

    private val resumeListAdapter: ResumeDelegateAdapter? = ResumeDelegateAdapter{

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        shimmerViewContainer.visibility = View.GONE
        setUpResumeAdapter()
        viewModel.resumes.observe(this.viewLifecycleOwner, this::updateAdapter)
    }

    private fun setUpResumeAdapter() {
        vacancies?.layoutManager = LinearLayoutManager(requireContext())
        vacancies?.adapter = resumeListAdapter
        vacancies?.bindFloatingActionButton(fab)
    }

    private fun updateAdapter(vacancies: List<Resume>?) {
        if (vacancies != null) {
            resumeListAdapter?.setData(vacancies)
        }
    }

    companion object {
        fun newInstance(): ResumeScreenFragment = ResumeScreenFragment()
    }
}

