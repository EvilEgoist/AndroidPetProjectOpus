package com.android.opus.domain

import com.android.opus.model.Resume
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class ResumeInteractor(
    private val dispatcher: CoroutineDispatcher
) {
    suspend fun loadResumes() : List<Resume>? =
        withContext(dispatcher) {
            ResumeMockData.getResult()
        }
}
