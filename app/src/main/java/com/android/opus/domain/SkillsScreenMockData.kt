package com.android.opus.domain

import com.android.opus.model.SkillsScreenField

object SkillsScreenMockData {
    private var chosenSkillsDataList = ArrayList<SkillsScreenField>()
    private var displayableDataList = ArrayList<SkillsScreenField>()
    private var mockData = mutableListOf<SkillsScreenField>(
            SkillsScreenField(0, "android", false),
            SkillsScreenField(1, "C++", false),
            SkillsScreenField(2, "MVVM", false),
            SkillsScreenField(3, "C#", false),
            SkillsScreenField(4, "Kotlin", false),
            SkillsScreenField(5, "CI/CD", false),
            SkillsScreenField(6, "Frontend developing", false),
            SkillsScreenField(7, "Backend", false),
            SkillsScreenField(8, "Swift", false),
            SkillsScreenField(9, "Python", false),
            SkillsScreenField(10,  "Java", false),
            SkillsScreenField(11, "IOS", false),
            SkillsScreenField(12,  "Java", false),
            SkillsScreenField(13, "Machine Learning", false),
    )

    fun getResult(): List<SkillsScreenField>? {
        return displayableDataList
    }

    fun refreshDisplayableList(){
        displayableDataList.clear()
        if(displayableDataList.size != 12) {
            for (i in 0..11) {
                displayableDataList.add(mockData[i])
            }
        }
    }

    fun addData(position: Int) {
        chosenSkillsDataList.add(SkillsScreenField(chosenSkillsDataList.size, displayableDataList[position].title, true))
        displayableDataList[position].isAdded = true
        for (i in 0 until displayableDataList.size)
            if (displayableDataList[i].id == position) {
                displayableDataList.removeAt(i)
                break
            }
        for (i in position until displayableDataList.size) {
            displayableDataList[i].id--
        }
    }

    fun searchItem( skill: String): ArrayList<SkillsScreenField> {
        displayableDataList.clear()
        for(i in 0 until mockData.size){
            if (mockData[i].title.contains(skill)){
                displayableDataList.add(SkillsScreenField(displayableDataList.size, mockData[i].title, false))
            }
        }
        return displayableDataList
    }

    fun getNewData(): List<SkillsScreenField>? {
        return chosenSkillsDataList
    }

    fun removeData(position: Int) {
        displayableDataList.add(SkillsScreenField(displayableDataList.size, chosenSkillsDataList[position].title , false))
        chosenSkillsDataList.removeAt(position)
        for (i in position until chosenSkillsDataList.size) {
            chosenSkillsDataList[i].id--
        }
    }
}
