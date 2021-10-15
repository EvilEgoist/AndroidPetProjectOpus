package com.android.opus.domain

import com.android.opus.model.Profile
import com.android.opus.model.ResumeInfo
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class ProfileInteractor(
    private val dispatcher: CoroutineDispatcher
) {
    suspend fun loadMainInfo() : Profile =
        withContext(dispatcher) {
            ProfileMockData.getResult()
        }
}