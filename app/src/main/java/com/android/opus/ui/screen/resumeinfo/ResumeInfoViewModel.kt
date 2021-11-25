package com.android.opus.ui.screen.resumeinfo

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.opus.domain.ResumeMainInfoInteractor
import com.android.opus.model.ResumeInfo
import kotlinx.coroutines.launch

class ResumeInfoViewModel(
    private val interactor: ResumeMainInfoInteractor,
) : ViewModel() {

    val resume = MutableLiveData<ResumeInfo>()
    val email = MutableLiveData<String>()

    init {
       loadResume()
    }

    fun loadResume() {
        viewModelScope.launch {
            resume.postValue(interactor.loadMainInfo())
        }
    }

    fun loadEmail() {
        viewModelScope.launch {
            email.postValue(resume.value?.email)
        }
    }
}
