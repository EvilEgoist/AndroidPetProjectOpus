package com.android.opus.domain

import com.android.opus.model.WorkPlace
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class WorkExperienceInteractor(
    private val dispatcher: CoroutineDispatcher,
) {
    suspend fun loadWorkPlaces() : List<WorkPlace>? =
        withContext(dispatcher) {
            WorkExperienceMockData.getResult()
        }
}
