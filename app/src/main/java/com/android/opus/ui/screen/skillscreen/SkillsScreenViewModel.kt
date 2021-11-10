package com.android.opus.ui.screen.skillscreen

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.opus.domain.SkillsScreenFacade
import com.android.opus.domain.SkillsScreenInteractor
import com.android.opus.model.SkillsScreenField
import kotlinx.coroutines.launch

class SkillsScreenViewModel(
        private val interactor: SkillsScreenInteractor
) : ViewModel() {

    val loadedSkills = MutableLiveData<List<SkillsScreenField>?>()
    val chosenSkills = MutableLiveData<List<SkillsScreenField>?>()

    init {
        loadSkillsScreenList()
    }

    private fun loadSkillsScreenList() {
        viewModelScope.launch {
            loadedSkills.postValue(interactor.loadSkillsScreenList())
        }
    }

    fun refreshFacade(){
        loadedSkills.postValue(SkillsScreenFacade.refreshDisplayableList())
    }

    fun searchSkill(query: String){
        loadedSkills.postValue(SkillsScreenFacade.searchItem(query))
    }

    fun addSkill(id: Int){
        loadedSkills.postValue(SkillsScreenFacade.addData(id))
        chosenSkills.postValue(SkillsScreenFacade.getNewData())
    }

    fun removeSkill(id: Int){
        chosenSkills.postValue(SkillsScreenFacade.removeData(id))
        loadedSkills.postValue(SkillsScreenFacade.getResult())
    }
}
