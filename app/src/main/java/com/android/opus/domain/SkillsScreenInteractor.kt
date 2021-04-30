package com.android.opus.domain

import com.android.opus.model.SkillsScreenField
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class SkillsScreenInteractor(
    private val dispatcher: CoroutineDispatcher,
) {
    suspend fun loadSkillsScreenList() : List<SkillsScreenField>? =
        withContext(dispatcher) {
            SkillsScreenFacade.getResult()
        }

    suspend fun loadChosenSkillsDataList() : List<SkillsScreenField>? =
            withContext(dispatcher) {
                SkillsScreenFacade.getNewData()
            }
}