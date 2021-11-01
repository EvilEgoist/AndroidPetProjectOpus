package com.android.opus.domain

import com.android.opus.model.Skill

object SkillsMockData {
    fun getResult(): List<Skill>? {
        return listOf(
            Skill(1, "MVVM"),
            Skill(2, "Administartive"),
            Skill(3, "Management"),
            Skill(4, "mvvm"),
            Skill(5, "Kotlin Multiplatform"),
            Skill(6, "Android"),
            Skill(7, "Ios"),
            Skill(8, "C++"),
            Skill(9, "JAVA"),
            Skill(10, "Jira"),
            Skill(11, "Word Press"),
            Skill(12, "Machine Learning"),
            Skill(13, "Artificial Intelligence")
        )
    }
}
