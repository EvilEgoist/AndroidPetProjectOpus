package com.android.opus.domain

import com.android.opus.model.FieldOfActivity
import com.android.opus.model.SkillsScreenField

object SkillsScreenMockData {
    private var chosenSkillsDataList = ArrayList<SkillsScreenField>()
    private var counter1 = 0
    private var counter2 = 0
    private var mockData = mutableListOf<SkillsScreenField>(
        SkillsScreenField(0, "android"),
        SkillsScreenField(1, "C++"),
        SkillsScreenField(2, "MVVM"),
        SkillsScreenField(3, "C#",),
        SkillsScreenField(4, "Kotlin"),
        SkillsScreenField(5, "CI/CD"),
        SkillsScreenField(6, "Frontend"),
        SkillsScreenField(7, "Backend"),
        SkillsScreenField(8, "Swift"),
        SkillsScreenField(9, "Python"),
        SkillsScreenField(10,  "Java"),
        SkillsScreenField(11, "Go"),
    )

    fun getResult(): List<SkillsScreenField>? {
        return mockData
    }

    fun addData(position: Int) {
        chosenSkillsDataList.add(SkillsScreenField(counter1, mockData[position].title))
        mockData.removeAt(position)
        for (i in position until mockData.size) {
            mockData[i].id--
        }
        ++counter1
        if (counter2 > 0 ) --counter2

    }

    fun getNewData(): List<SkillsScreenField>? {
        return chosenSkillsDataList
    }

    fun removeData(position: Int) {
        mockData.add(SkillsScreenField(counter2, chosenSkillsDataList[position].title ))
        chosenSkillsDataList.removeAt(position)
        for (i in position until chosenSkillsDataList.size) {
            chosenSkillsDataList[i].id--
        }
        if (counter1 > 0 ) --counter1
        ++counter2
    }

}
