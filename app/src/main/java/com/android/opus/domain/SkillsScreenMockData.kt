package com.android.opus.domain

import com.android.opus.model.SkillsScreenField

object SkillsScreenMockData {
    private var chosenSkillsDataList = ArrayList<SkillsScreenField>()
    private var displayableDataList = ArrayList<SkillsScreenField>()
    private var mockData = mutableMapOf<Int, SkillsScreenField>(
            0 to SkillsScreenField(0, "android"),
            1 to SkillsScreenField(1, "C++"),
            2 to SkillsScreenField(2, "MVVM"),
            3 to SkillsScreenField(3, "C#"),
            4 to SkillsScreenField(4, "Kotlin"),
            5 to SkillsScreenField(5, "CI/CD"),
            6 to SkillsScreenField(6, "Frontend developing"),
            7 to SkillsScreenField(7, "Backend"),
            8 to SkillsScreenField(8, "Swift"),
            9 to SkillsScreenField(9, "Python"),
            10 to SkillsScreenField(10, "Java"),
            11 to SkillsScreenField(11, "IOS"),
            12 to SkillsScreenField(12, "Ruby"),
            13 to SkillsScreenField(13, "Machine Learning"),
    )

    fun getResult(): List<SkillsScreenField>? {
        return displayableDataList
    }

    fun refreshDisplayableList(){
        if(displayableDataList.size != 12) {
            displayableDataList.clear()
            var iter = mockData.iterator()
            while (iter.hasNext() && displayableDataList.size < 12) {
                displayableDataList.add(iter.next().component2())
            }
        }
    }

    fun addData(position: Int) {
        for (i in 0 until displayableDataList.size)
            if (displayableDataList[i].id == position) {
                chosenSkillsDataList.add(displayableDataList[i])
                displayableDataList.removeAt(i)
                mockData.remove(position)
                break
            }
    }

    fun searchItem(skill: String): ArrayList<SkillsScreenField> {
        displayableDataList.clear()
        val iter = mockData.iterator()
        while(iter.hasNext()){
            val item = iter.next().component2()
            if (item.title.contains(skill)){
                displayableDataList.add(item)
            }
        }
        return displayableDataList
    }

    fun getNewData(): List<SkillsScreenField>? {
        return chosenSkillsDataList
    }

    fun removeData(position: Int) {
        for (i in 0 until chosenSkillsDataList.size)
            if (chosenSkillsDataList[i].id == position) {
                displayableDataList.add(chosenSkillsDataList[i])
                mockData[position] = chosenSkillsDataList[i]
                chosenSkillsDataList.removeAt(i)
                break
            }
    }
}
