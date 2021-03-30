package com.android.opus.ui.screen.skillscreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.opus.domain.ChosenSkillInteractor
import com.android.opus.model.SkillsScreenField
import kotlinx.coroutines.launch

class ChosenSkillViewModel(
        private val interactor: ChosenSkillInteractor
) : ViewModel() {

    private val _newMutableSCFields = MutableLiveData<List<SkillsScreenField>?>()
    val newSCFields: LiveData<List<SkillsScreenField>?> get() = _newMutableSCFields

    init {
        loadSkillsScreenList()
    }

    private fun loadSkillsScreenList() {
        viewModelScope.launch {
            _newMutableSCFields.postValue(interactor.loadNewDataList())
        }
    }
}
