package com.android.opus.domain

import android.app.Activity
import android.content.Context
import android.util.Log
import com.android.opus.R
import com.android.opus.model.FieldOfActivity
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