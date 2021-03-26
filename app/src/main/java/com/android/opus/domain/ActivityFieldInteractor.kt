package com.android.opus.domain

import com.android.opus.model.FieldOfActivity
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class ActivityFieldInteractor(
    private val dispatcher: CoroutineDispatcher,
) {
    suspend fun loadActivityFieldList() : List<FieldOfActivity>? =
        withContext(dispatcher) {
            FieldOfActivityMockData.getResult()
        }
}
