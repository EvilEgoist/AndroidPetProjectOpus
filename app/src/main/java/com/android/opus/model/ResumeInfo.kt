package com.android.opus.model

import com.android.opus.common.DisplayableItem

data class Resume(
    val id: Int,
    val imageUrl: String?,
    val jobPosition: String,
    val employeeName: String,
    val experience: String,
    val salary: String,
    val level: String,
    val description: String?,
    val skills: List<Skill>?
) : DisplayableItem {

    override fun id() = id

    override fun content() = VacancyContent(
        experience,
        salary,
        level,
        description,
        skills
    )

    inner class VacancyContent(
        private val experience: String,
        private val salary: String,
        private val level: String,
        private val description: String?,
        private val skills: List<Skill>?
    ) {
        override fun equals(other: Any?): Boolean {
            if (other is VacancyContent) {
                return experience == other.experience && salary == other.salary
                        && level == other.level && description == other.description && skills == other.skills
            }
            return false
        }

        override fun hashCode(): Int {
            var result = experience.hashCode()
            result = 31 * result + salary.hashCode()
            result = 31 * result + level.hashCode()
            result = 31 * result + description.hashCode()
            result = 31 * result + skills.hashCode()
            return result
        }
    }
}