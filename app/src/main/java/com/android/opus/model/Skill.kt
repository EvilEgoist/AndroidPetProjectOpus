package com.android.opus.model

import com.android.opus.common.DisplayableItem

data class Skill(
    val id: Int,
    val title: String
) : DisplayableItem {
    override fun id() = id

    override fun content() = SkillContent(
        title
    )

    inner class SkillContent(
        private val title: String
    ) {
        override fun equals(other: Any?): Boolean {
            if (other is SkillContent) {
                return title == other.title
            }
            return false
        }

        override fun hashCode(): Int {
            val result = 31 * title.hashCode()
            return result
        }
    }

}