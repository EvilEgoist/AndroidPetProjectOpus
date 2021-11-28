package com.android.opus.ui.screen.company_profile

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.opus.domain.CompanyProfileInteractor
import com.android.opus.model.CompanyProfile
import kotlinx.coroutines.launch

class CompanyProfileViewModel (
        private val interactor: CompanyProfileInteractor,
) : ViewModel() {

    val companyProfile = MutableLiveData<CompanyProfile>();

    init {
        loadCompanyProfile()
    }

    fun loadCompanyProfile() {
        viewModelScope.launch {
            companyProfile.postValue(interactor.loadCompanyProfile())
        }
    }
}