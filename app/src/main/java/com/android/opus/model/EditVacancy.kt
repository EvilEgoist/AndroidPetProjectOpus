package com.android.opus.model

import com.android.opus.common.adapters.vacancy.edit.VacancyDisplayableItem


class EditVacancy(
    var vacancy: ExpandedVacancy,
    var loadedSkills: List<Skill>
) : VacancyDisplayableItem {

    private var loadedSkillsListener: SkillChangeListener? = null
    private var chosenSkillsListener: SkillChangeListener? = null

    override fun id() = vacancy.id

    override fun content() = EditWorkPlaceContent(
        vacancy,
        loadedSkills
    )

    fun containsMainInfo(): Boolean {
        return vacancy.employerName.isNotEmpty()
    }

    inner class EditWorkPlaceContent(
        private val vacancy: ExpandedVacancy,
        private val loadedSkills: List<Skill>
    ) {
        private val PRIME = 31

        override fun equals(other: Any?): Boolean {
            if (other is EditWorkPlaceContent) {
                return vacancy == other.vacancy && loadedSkills == other.loadedSkills

            }
            return false
        }

        override fun hashCode(): Int {
            var result = vacancy.hashCode()
            result = PRIME * result + loadedSkills.hashCode()
            return result
        }
    }

    fun addSkillToChosen(id: Int) {
        val listLoadedSkills: MutableList<Skill> = loadedSkills.toMutableList()
        val listChosenSkills: MutableList<Skill> = vacancy.skills.toMutableList()
        val iterator = listLoadedSkills.iterator()
        while (iterator.hasNext()) {
            val item = iterator.next()
            if (item.id == id) {
                listChosenSkills.add(item)
                iterator.remove()
            }
        }
        vacancy.skills = listChosenSkills
        loadedSkills = listLoadedSkills

        loadedSkillsListener?.invoke(loadedSkills)
        chosenSkillsListener?.invoke(vacancy.skills)
    }

    fun replaceSkillFromChosen(id: Int) {
        val listLoadedSkills: MutableList<Skill> = loadedSkills.toMutableList()
        val listChosenSkills: MutableList<Skill> = vacancy.skills.toMutableList()
        val iterator = listChosenSkills.iterator()
        while (iterator.hasNext()) {
            val item = iterator.next()
            if (item.id == id) {
                listLoadedSkills.add(item)
                iterator.remove()
            }
        }
        vacancy.skills = listChosenSkills
        loadedSkills = listLoadedSkills

        loadedSkillsListener?.invoke(loadedSkills)
        chosenSkillsListener?.invoke(vacancy.skills)
    }

    fun addLoadedSkillListener(listener: SkillChangeListener) {
        loadedSkillsListener = listener
    }

    fun addChosenSkillListener(listener: SkillChangeListener) {
        chosenSkillsListener = listener
    }
}

