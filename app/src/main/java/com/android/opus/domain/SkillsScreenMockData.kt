package com.android.opus.domain

import com.android.opus.model.FieldOfActivity
import com.android.opus.model.SkillsScreenField

object SkillsScreenMockData {
    private var chosenSkillsDataList = ArrayList<SkillsScreenField>()
    private var counter = 0
    private var mockData = listOf(
            FieldOfActivity(0, "android", false),
            FieldOfActivity(1, "C++", false),
            FieldOfActivity(2, "MVVM", false),
            FieldOfActivity(3, "C#", false),
            FieldOfActivity(4, "Kotlin", false),
            FieldOfActivity(5, "CI/CD", false),
            FieldOfActivity(6, "Frontend", false),
            FieldOfActivity(7, "Backend", false),
            FieldOfActivity(8, "Swift", false),
            FieldOfActivity(9, "Python", false),
            FieldOfActivity(10, "Java", false),
            FieldOfActivity(11, "Go", false),
    )

    fun getResult(): List<FieldOfActivity>? {
        return mockData
    }

    fun addData(position: Int) {
        if (!mockData[position].isChecked) {
            chosenSkillsDataList.add(SkillsScreenField(counter, position, mockData[position].title))
            mockData[position].isChecked = true
            ++counter
        }
    }

    fun getNewData(): List<SkillsScreenField>? {
        return chosenSkillsDataList
    }

    fun removeData(position: Int) {
        mockData[chosenSkillsDataList[position].addId].isChecked = false
        chosenSkillsDataList.removeAt(position)
        for (i in position until chosenSkillsDataList.size) {
            chosenSkillsDataList[i].id--
        }
        --counter
    }

}
