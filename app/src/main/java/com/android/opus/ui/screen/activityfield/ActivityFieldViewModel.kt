package com.android.opus.ui.screen.activityfield

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.opus.domain.ActivityFieldInteractor
import com.android.opus.model.FieldOfActivity
import kotlinx.coroutines.launch

class ActivityFieldViewModel(
        private val interactor: ActivityFieldInteractor
) : ViewModel() {

    private val _mutableActivityFields = MutableLiveData<List<FieldOfActivity>?>()
    val activityFields: LiveData<List<FieldOfActivity>?> get() = _mutableActivityFields

    init {
        loadActivityFieldList()
    }

    private fun loadActivityFieldList() {
        viewModelScope.launch {
            _mutableActivityFields.postValue(interactor.loadActivityFieldList())
        }
    }
}
