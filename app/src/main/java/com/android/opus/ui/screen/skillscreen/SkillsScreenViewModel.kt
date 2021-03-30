package com.android.opus.ui.screen.skillscreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.opus.domain.SkillsScreenInteractor
import com.android.opus.model.FieldOfActivity
import kotlinx.coroutines.launch

class SkillsScreenViewModel(
    private val interactor: SkillsScreenInteractor
) : ViewModel() {

    private val _mutableSCFields = MutableLiveData<List<FieldOfActivity>?>()
    val SCFields: LiveData<List<FieldOfActivity>?> get() = _mutableSCFields

    init {
        loadSkillsScreenLists()
    }

    private fun loadSkillsScreenLists() {
        viewModelScope.launch {
            _mutableSCFields.postValue(interactor.loadSkillsScreenList())
        }
    }
}
