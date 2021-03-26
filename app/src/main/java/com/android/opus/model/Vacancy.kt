package com.android.opus.model

import android.R.attr.name
import com.android.opus.common.DisplayableItem

data class Vacancy(
    val id: Int,
    val titleVacancy: String,
    val employerName: String,
    val salary: String?,
    val description: String?,
    val imageUrl: String?,
    val skills: List<Skill>
) : DisplayableItem {

    override fun id() = id

    override fun content() = VacancyContent(
        titleVacancy,
        employerName,
        skills
    )

    inner class VacancyContent(
        private val salary: String?,
        private val description: String?,
        private val skills: List<Skill>
    ) {
        override fun equals(other: Any?): Boolean {
            if (other is VacancyContent) {
                return salary == other.salary && description == other.description && skills == other.skills
            }
            return false
        }

        override fun hashCode(): Int {
            var result = salary.hashCode()
            result = 31 * result + description.hashCode()
            result = 31 * result + skills.hashCode()
            return result
        }
    }
}
