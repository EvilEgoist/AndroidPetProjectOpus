package com.android.opus.ui.screen.activityfield

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.opus.domain.ActivityFieldInteractor
import kotlinx.coroutines.launch

class ActivityFieldViewModel(
        private val interactor: ActivityFieldInteractor
) : ViewModel() {

    private val _mutableActivityFields = MutableLiveData<List<String>?>()
    val activityFields: LiveData<List<String>?> get() = _mutableActivityFields

    fun loadActivityFieldList() {
        viewModelScope.launch {
            _mutableActivityFields.postValue(interactor.loadActivityFieldList())
        }
    }
}
