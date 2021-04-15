package com.android.opus.domain

import android.content.Context
import com.android.opus.R
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class SkillsScreenInteractor(
        private val dispatcher: CoroutineDispatcher,
        private val context: Context?
) {
    suspend fun loadSkillsScreenList() : List<String>? =
            withContext(dispatcher) {
                context?.resources?.getStringArray(R.array.skills_arr)?.toList()
            }
}