package com.android.opus.model

import com.android.opus.common.adapters.vacancy.edit.VacancyDisplayableItem

data class ExpandedVacancy(
        val id: Int,
        val titleVacancy: String,
        var employerName: String,
        val salary: String,
        var description: String,
        var workFullDay: Boolean,
        var level: String,
        var skills: List<Skill>,
        val companyDescr: String,
        val location: String,
        val typeOfEmployment: String,
        var responsibilities: List<ShortDescription>,
        var requirements: List<ShortDescription>,
        var conditions: List<ShortDescription>
): VacancyDisplayableItem {

        override fun id() = id

        override fun content() = VacancyContent(
                salary,
                description,
                skills,
                companyDescr,
                location,
                typeOfEmployment,
                responsibilities,
                requirements,
                conditions
        )

        inner class VacancyContent(
                private val salary: String?,
                private val description: String?,
                private val skills: List<Skill>?,
                private val companyDescr: String?,
                private val location: String?,
                private  val typeOfEmployment: String?,
                private val responsibilities: List<ShortDescription>,
                private val requirements: List<ShortDescription>,
                private val conditions: List<ShortDescription>
        ) {
                override fun equals(other: Any?): Boolean {
                        if (other is VacancyContent) {
                                return salary == other.salary && description == other.description && skills == other.skills &&
                                        companyDescr == other.companyDescr && location == other.location && typeOfEmployment == other.typeOfEmployment &&
                                        responsibilities == other.responsibilities && requirements == other.requirements && conditions == other.conditions
                        }
                        return false
                }

                override fun hashCode(): Int {
                        var result = salary.hashCode()
                        result = 31 * result + description.hashCode()
                        result = 31 * result + skills.hashCode()
                        result = 31 * result + companyDescr.hashCode()
                        result = 31 * result + location.hashCode()
                        result = 31 * result + typeOfEmployment.hashCode()
                        result = 31 * result + responsibilities.hashCode()
                        result = 31 * result + requirements.hashCode()
                        result = 31 * result + conditions.hashCode()
                        return result
                }
        }
}

