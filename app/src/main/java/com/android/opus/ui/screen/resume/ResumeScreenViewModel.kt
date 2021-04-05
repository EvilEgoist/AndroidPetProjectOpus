package com.android.opus.ui.screen.resume

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.opus.domain.ResumeInteractor
import com.android.opus.model.Resume
import kotlinx.coroutines.launch

class ResumeScreenViewModel(
    private val interactor: ResumeInteractor
) : ViewModel() {

    val resumes = MutableLiveData<List<Resume>?>()

    init {
        loadResumes()
    }

    private fun loadResumes() {
        viewModelScope.launch {
            resumes.postValue(interactor.loadResumes())
        }
    }
}
