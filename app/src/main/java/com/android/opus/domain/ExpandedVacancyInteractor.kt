package com.android.opus.domain

import com.android.opus.model.ExpandedVacancy
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class ExpandedVacancyInteractor(
    private val dispatcher: CoroutineDispatcher
    ) {
    suspend fun loadExpVacancyDescr(): ExpandedVacancy =
            withContext(dispatcher) {
                ExpandedVacancyMockData.getResult()
            }
}