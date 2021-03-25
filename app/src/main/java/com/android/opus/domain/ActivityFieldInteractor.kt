package com.android.opus.domain

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class ActivityFieldInteractor(
    private val dispatcher: CoroutineDispatcher,
) {
    suspend fun loadActivityFieldList() : List<String>? =
        withContext(dispatcher) {
            FieldOfActivityMockData.getResult()
        }
}
