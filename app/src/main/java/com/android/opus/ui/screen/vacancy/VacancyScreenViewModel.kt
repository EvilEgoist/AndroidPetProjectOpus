package com.android.opus.ui.screen.vacancy

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.opus.domain.VacancyInteractor
import com.android.opus.model.Vacancy
import kotlinx.coroutines.launch

class VacancyScreenViewModel(
    private val interactor: VacancyInteractor
) : ViewModel() {
    private val _mutableVacancies = MutableLiveData<List<Vacancy>?>()
    val vacancies: LiveData<List<Vacancy>?> get() =  _mutableVacancies

    init {
        loadVacancies()
    }

    private fun loadVacancies() {
        viewModelScope.launch {
            _mutableVacancies.postValue(interactor.loadVacancies())
        }
    }
}
