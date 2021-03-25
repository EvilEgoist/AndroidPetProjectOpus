package com.android.opus.ui.screen.skillscreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.opus.domain.SkillsScreenInteractor
import kotlinx.coroutines.launch

class SkillsScreenViewModel(
        private val interactor: SkillsScreenInteractor
) : ViewModel() {

    private val _mutableSkillsScreen = MutableLiveData<List<String>?>()
    val skillsScreen: LiveData<List<String>?>get () = _mutableSkillsScreen

    fun loadSkillsScreenList(){
        viewModelScope.launch {
            _mutableSkillsScreen.postValue(interactor.loadSkillsScreenList())
        }
    }
}
