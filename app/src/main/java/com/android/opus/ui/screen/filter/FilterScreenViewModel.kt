package com.android.opus.ui.screen.filter

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.opus.domain.ActivityFieldInteractor
import com.android.opus.domain.FieldOfActivityMockData
import com.android.opus.domain.SkillsInteractor
import com.android.opus.domain.SkillsMockData
import com.android.opus.model.FieldOfActivity
import com.android.opus.model.Skill
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FilterScreenViewModel(
    private val activityFieldInteractor: ActivityFieldInteractor,
    private val skillsInteractor: SkillsInteractor
) : ViewModel() {

    val activityFields = MutableLiveData<List<FieldOfActivity>?>()
    val chosenSkills = MutableLiveData<List<Skill>?>()
    val loadedSkills = MutableLiveData<List<Skill>?>()

    fun loadActivityFieldList() {
        viewModelScope.launch {
            activityFields.postValue(activityFieldInteractor.loadActivityFieldList())
        }
    }

    fun loadSkills() {
        viewModelScope.launch {
            loadedSkills.postValue(skillsInteractor.loadSkillsScreenList())
        }
    }

    fun addSkillToChosen(id: Int) {
        viewModelScope.launch {
            val listLoadedSkills: MutableList<Skill>? = loadedSkills.value?.toMutableList()
            var listChosenSkills: MutableList<Skill>? = chosenSkills.value?.toMutableList()
            listLoadedSkills?.let {
                val iterator = listLoadedSkills.iterator()
                while (iterator.hasNext()) {
                    val item = iterator.next()
                    if (item.id == id) {
                        listChosenSkills?.add(item) ?: listChosenSkills.apply {
                            listChosenSkills = mutableListOf(item)
                        }
                        iterator.remove()
                    }
                }
            }
            chosenSkills.postValue(listChosenSkills)
            loadedSkills.postValue(listLoadedSkills)
        }
    }

    fun replaceSkillFromChosen(id: Int) {
        viewModelScope.launch {
            val listLoadedSkills: MutableList<Skill>? = loadedSkills.value?.toMutableList()
            val listChosenSkills: MutableList<Skill>? = chosenSkills.value?.toMutableList()
            listChosenSkills?.let {
                val iterator = listChosenSkills.iterator()
                while (iterator.hasNext()) {
                    val item = iterator.next()
                    if (item.id == id) {
                        listLoadedSkills?.add(item)
                        iterator.remove()
                    }
                }
            }
            chosenSkills.postValue(listChosenSkills)
            loadedSkills.postValue(listLoadedSkills)
        }
    }
}
