package com.android.opus.domain

import com.android.opus.model.CompanyProfile
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class CompanyProfileInteractor (
        private val dispatcher: CoroutineDispatcher
) {
    suspend fun loadCompanyProfile() : CompanyProfile =
            withContext(dispatcher) {
                CompanyProfileMockData.getResult()
            }
}