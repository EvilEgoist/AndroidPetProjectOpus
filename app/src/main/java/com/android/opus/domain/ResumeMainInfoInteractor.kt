package com.android.opus.domain

import com.android.opus.model.ResumeInfo
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class ResumeMainInfoInteractor(
    private val dispatcher: CoroutineDispatcher
) {
    suspend fun loadMainInfo() : ResumeInfo =
        withContext(dispatcher) {
            ResumeInfoMockData.getResult()
        }
}