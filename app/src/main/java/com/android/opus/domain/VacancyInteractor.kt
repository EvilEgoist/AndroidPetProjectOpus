package com.android.opus.domain

import com.android.opus.model.Vacancy
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class VacancyInteractor(
    private val dispatcher: CoroutineDispatcher
) {
    suspend fun loadVacancies() : List<Vacancy>? =
        withContext(dispatcher) {
            VacanciesMockData.getResult()
        }
}
