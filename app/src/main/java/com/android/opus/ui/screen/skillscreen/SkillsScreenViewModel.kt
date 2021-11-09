package com.android.opus.ui.screen.skillscreen

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.opus.domain.SkillsScreenInteractor
import com.android.opus.model.SkillsScreenField
import kotlinx.coroutines.launch

class SkillsScreenViewModel(
        private val interactor: SkillsScreenInteractor
) : ViewModel() {

    val skillsScreenFields = MutableLiveData<List<SkillsScreenField>?>()

    init {
        loadSkillsScreenList()
    }

    private fun loadSkillsScreenList() {
        viewModelScope.launch {
            skillsScreenFields.postValue(interactor.loadSkillsScreenList())
        }
    }
}