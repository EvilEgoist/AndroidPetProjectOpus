package com.android.opus.domain

import com.android.opus.model.Skill
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class SkillsInteractor(
    private val dispatcher: CoroutineDispatcher,
) {
    suspend fun loadSkills() : List<Skill>? =
        withContext(dispatcher) {
            SkillMockData.getResult()
        }
}
