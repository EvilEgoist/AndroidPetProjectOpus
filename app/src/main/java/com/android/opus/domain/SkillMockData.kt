package com.android.opus.domain

import com.android.opus.model.Skill

object SkillMockData {
    fun getResult(): List<Skill>? {
        return listOf(
            Skill(
                12,
                "Android"
            ),
            Skill(
                13,
                "MVVM"
            ),
            Skill(
                14,
                "Administrative"
            ),
            Skill(
                12,
                "iOS"
            ),
            Skill(
                13,
                "BACKEND"
            ),
            Skill(
                14,
                "Dagger"
            ),
            Skill(
                12,
                "Retrofit"
            ),
            Skill(
                13,
                "Kotlin Multiplatform"
            ),
            Skill(
                14,
                "RxJava2"
            ),
            Skill(
                12,
                "Clean Architecture"
            )
        )
    }
}
