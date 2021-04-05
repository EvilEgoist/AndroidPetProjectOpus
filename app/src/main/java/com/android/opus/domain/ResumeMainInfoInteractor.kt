package com.android.opus.domain

import com.android.opus.model.Resume
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class ResumeMainInfoInteractor(
    private val dispatcher: CoroutineDispatcher
) {
    suspend fun loadMainInfo() : Resume =
        withContext(dispatcher) {
            ResumeMockData.getResult()
        }
}