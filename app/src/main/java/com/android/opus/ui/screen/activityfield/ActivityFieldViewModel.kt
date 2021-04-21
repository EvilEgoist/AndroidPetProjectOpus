package com.android.opus.ui.screen.activityfield

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.opus.domain.ActivityFieldInteractor
import com.android.opus.model.FieldOfActivity
import kotlinx.coroutines.launch

class ActivityFieldViewModel(
        private val interactor: ActivityFieldInteractor
) : ViewModel() {

    val activityFields = MutableLiveData<List<FieldOfActivity>?>()

    init {
        loadActivityFieldList()
    }

    private fun loadActivityFieldList() {
        viewModelScope.launch {
            activityFields.postValue(interactor.loadActivityFieldList())
        }
    }
}
