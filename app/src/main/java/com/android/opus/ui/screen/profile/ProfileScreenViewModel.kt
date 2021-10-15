package com.android.opus.ui.screen.profile

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.opus.domain.ProfileInteractor
import com.android.opus.model.Profile
import kotlinx.coroutines.launch

class ProfileScreenViewModel(
    private val interactor: ProfileInteractor,
) : ViewModel() {

    val resume = MutableLiveData<Profile>();

    init {
        loadProfile()
    }

    fun loadProfile() {
        viewModelScope.launch {
            resume.postValue(interactor.loadMainInfo())
        }
    }
}
