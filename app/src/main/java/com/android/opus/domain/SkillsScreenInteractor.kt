package com.android.opus.domain

import com.android.opus.model.FieldOfActivity
import com.android.opus.model.SkillsScreenField
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class SkillsScreenInteractor(
    private val dispatcher: CoroutineDispatcher,
) {
    suspend fun loadSkillsScreenList() : List<FieldOfActivity>? =
        withContext(dispatcher) {
            SkillsScreenMockData.getResult()
        }
}