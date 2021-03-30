package com.android.opus.domain

import com.android.opus.model.SkillsScreenField
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class ChosenSkillInteractor(
        private val dispatcher: CoroutineDispatcher,
) {
    suspend fun loadNewDataList(): List<SkillsScreenField>? =
            withContext(dispatcher){
                SkillsScreenMockData.getNewData()
            }
}