package com.android.opus.ui.screen.resumeinfo

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.opus.domain.ResumeMainInfoInteractor
import com.android.opus.model.Resume
import kotlinx.coroutines.launch

class ResumeInfoViewModel(
    private val interactor: ResumeMainInfoInteractor,
) : ViewModel() {

    val resume = MutableLiveData<Resume>();

    init {
       loadResume()
    }

    fun loadResume() {
        viewModelScope.launch {
            resume.postValue(interactor.loadMainInfo())
        }
    }
}
