package com.android.opus.domain

import com.android.opus.model.Skill
import com.android.opus.model.SkillsScreenField

object SkillsScreenMockData {
    private var chosenSkillsDataList = ArrayList<SkillsScreenField>()
    private var mockData = mutableListOf<SkillsScreenField>(
        SkillsScreenField(0, "android"),
        SkillsScreenField(1, "C++"),
        SkillsScreenField(2, "MVVM"),
        SkillsScreenField(3, "C#"),
        SkillsScreenField(4, "Kotlin"),
        SkillsScreenField(5, "CI/CD"),
        SkillsScreenField(6, "Frontend developing"),
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
        chosenSkillsDataList.add(SkillsScreenField(chosenSkillsDataList.size, mockData[position].title))
        mockData.removeAt(position)
        for (i in position until mockData.size) {
            mockData[i].id--
        }
    }

    fun searchItem( skill: String): ArrayList<SkillsScreenField> {
        val list = ArrayList<SkillsScreenField>()
        for(i in 0 until mockData.size){
            if (mockData[i].title.contains(skill)){
                list.add(SkillsScreenField(mockData[i].id, mockData[i].title))
            }
        }
        return list
    }

    fun getNewData(): List<SkillsScreenField>? {
        return chosenSkillsDataList
    }

    fun removeData(position: Int) {
        mockData.add(SkillsScreenField(mockData.size, chosenSkillsDataList[position].title ))
        chosenSkillsDataList.removeAt(position)
        for (i in position until chosenSkillsDataList.size) {
            chosenSkillsDataList[i].id--
        }
    }
}
