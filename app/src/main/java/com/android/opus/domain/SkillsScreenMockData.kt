package com.android.opus.domain

import com.android.opus.model.SkillsScreenField

object SkillsScreenMockData {
    private var chosenSkillsDataList = ArrayList<SkillsScreenField>()
    private var displayableDataList = ArrayList<SkillsScreenField>()
    private var counter = 12
    private var mockData = mutableMapOf<Int, SkillsScreenField>(
            0 to SkillsScreenField(0, "android"),
            1 to SkillsScreenField(1, "C++"),
            2 to SkillsScreenField(2, "MVVM"),
            3 to SkillsScreenField(3, "C#"),
            4 to SkillsScreenField(4, "Kotlin"),
            5 to SkillsScreenField(5, "CI/CD"),
            6 to SkillsScreenField(6, "Frontend"),
            7 to SkillsScreenField(7, "Backend"),
            8 to SkillsScreenField(8, "Swift"),
            9 to SkillsScreenField(9, "Python"),
            10 to SkillsScreenField(10, "Java"),
            11 to SkillsScreenField(11, "IOS"),
            12 to SkillsScreenField(12, "Ruby"),
            13 to SkillsScreenField(13, "Machine Learning"),
            14 to SkillsScreenField(14, "Some data 1"),
            15 to SkillsScreenField(15, "Some data 2"),
            16 to SkillsScreenField(16, "Some data 3"),
            17 to SkillsScreenField(17, "Some data 4"),
            18 to SkillsScreenField(18, "Some data 5"),
            19 to SkillsScreenField(19, "Some data 6"),
            20 to SkillsScreenField(20, "Some data 7"),
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
        if (displayableDataList.size == 11 && mockData.size > 11) {
            val iter = mockData.iterator()
            var counter = 0
            if (iter.hasNext()) {
                for (i in 0..10) {
                    iter.next()
                }
                displayableDataList.add(iter.next().component2())
            }
        }
    }

    fun searchItem(query: String): ArrayList<SkillsScreenField> {
        val listiter = displayableDataList.iterator()
        while (listiter.hasNext()) {
            var temp = listiter.next()
            if (!temp.title.contains(query)) {
                listiter.remove()
            }
        }
        displayableDataList.clear()
        val iter = mockData.iterator()
        while(iter.hasNext()){
            val item = iter.next().component2()
            if (item.title.contains(query)){
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
                if (displayableDataList.size < 12) displayableDataList.add(chosenSkillsDataList[i])
                mockData[position] = chosenSkillsDataList[i]
                chosenSkillsDataList.removeAt(i)
                break
            }
    }
}
