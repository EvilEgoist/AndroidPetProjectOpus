package com.android.opus.model

import com.android.opus.common.DisplayableItem

data class WorkPlace(
    val id: Int,
    val imageUrl: String?,
    val title: String,
    val placeDescription: String,
    val jobPosition: String,
    val workPeriod: String,
    val responsibilities: String?,
    val skills: List<Skill>?
) : DisplayableItem {
    override fun id() = id

    override fun content() = WorkPlaceContent(
        title,
        placeDescription,
        jobPosition,
        workPeriod,
        responsibilities,
        skills
    )

    inner class WorkPlaceContent(
        private val title: String,
        private val placeDescription: String,
        private val jobPosition: String,
        private val workPeriod: String,
        private val responsibilities: String?,
        private val skills: List<Skill>?
    ) {
        override fun equals(other: Any?): Boolean {
            if (other is WorkPlaceContent) {
                return title == other.title && placeDescription == other.placeDescription
                        && jobPosition == other.jobPosition && workPeriod == other.workPeriod
                        && responsibilities == other.responsibilities && skills == other.skills
            }
            return false
        }

        override fun hashCode(): Int {
            var result = title.hashCode()
            result = 31 * result + placeDescription.hashCode()
            result = 31 * result + jobPosition.hashCode()
            result = 31 * result + workPeriod.hashCode()
            result = 31 * result + responsibilities.hashCode()
            result = 31 * result + skills.hashCode()
            return result
        }
    }

}
