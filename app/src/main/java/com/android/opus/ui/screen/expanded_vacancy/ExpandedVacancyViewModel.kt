package com.android.opus.ui.screen.expanded_vacancy

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.opus.domain.ExpandedVacancyInteractor
import com.android.opus.model.ExpandedVacancy
import kotlinx.coroutines.launch

class ExpandedVacancyViewModel(
        private val interactor: ExpandedVacancyInteractor,
) : ViewModel() {

    val expandedVacancy = MutableLiveData<ExpandedVacancy>();

    init {
        loadExpandedVacancy()
    }

    fun loadExpandedVacancy() {
        viewModelScope.launch {
            expandedVacancy.postValue(interactor.loadExpVacancyDescr())
        }
    }
}