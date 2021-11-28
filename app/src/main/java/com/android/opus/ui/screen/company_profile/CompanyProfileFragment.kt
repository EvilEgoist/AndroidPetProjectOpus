package com.android.opus.ui.screen.company_profile

import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.opus.R
import com.android.opus.domain.CompanyProfileInteractor
import com.android.opus.domain.ResumeInteractor
import com.android.opus.model.CompanyProfile
import com.android.opus.model.Resume
import com.android.opus.ui.screen.resume.ResumeDelegateAdapter
import com.android.opus.ui.screen.resume.ResumeScreenFragment
import com.android.opus.ui.screen.resume.ResumeScreenViewModel
import com.android.opus.utils.bindFloatingActionButton
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.fragment_company_profile.*
import kotlinx.android.synthetic.main.fragment_vacanies.*
import kotlinx.android.synthetic.main.item_company_profile.*
import kotlinx.android.synthetic.main.item_resume.view.*
import kotlinx.coroutines.Dispatchers

class CompanyProfileFragment: Fragment(R.layout.fragment_company_profile) {

    private var viewModel = CompanyProfileViewModel(
        CompanyProfileInteractor(dispatcher = Dispatchers.IO)
    )

    private val companyProfileAdapter: CompanyProfileAdapter? = CompanyProfileAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpCompanyProfileAdapter()
        viewModel.companyProfile.observe(this.viewLifecycleOwner, this::updateAdapter)
    }

    private fun setUpCompanyProfileAdapter() {
        vacanciesRecycler?.layoutManager = LinearLayoutManager(requireContext())
        vacanciesRecycler?.adapter = companyProfileAdapter

    }

    private fun updateAdapter(companyData: CompanyProfile) {
        updateMainInfo(companyData)
        if (companyData.vacancies != null) {
            companyProfileAdapter?.setData(companyData.vacancies)
        }
    }

    private fun updateMainInfo(companyData: CompanyProfile){
        company_name.text = companyData.companyName
        context?.let {
            Glide.with(it)
                .load(companyData.imageUrl)
                .into(company_photo)
        }
        companyLocation.text = companyData.companyLocation
        companyDescription.text = companyData.shortCompanyDescr
    }

    companion object {
        fun newInstance(): CompanyProfileFragment = CompanyProfileFragment()
    }
}