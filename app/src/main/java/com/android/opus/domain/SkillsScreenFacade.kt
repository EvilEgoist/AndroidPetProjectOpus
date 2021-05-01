package com.android.opus.domain

import com.android.opus.model.SkillsScreenField

object SkillsScreenFacade{
    private val chosenSkillsDataList = ArrayList<SkillsScreenField>()
    private var counter = 0
    private var searchFlag = false
    private val displayableDataList = ArrayList<SkillsScreenField>()
    private var addedSkillsMap = sortedMapOf<Int, SkillsScreenField>()
    private var nonAddedSkillsMap = sortedMapOf<Int, SkillsScreenField>(
        0 to SkillsScreenField(0, 0,"android", false),
        1 to SkillsScreenField(1, 1,"C++", false),
        2 to SkillsScreenField(2, 2,"MVVM", false),
        3 to SkillsScreenField(3, 3, "C#", false),
        4 to SkillsScreenField(4, 4, "Kotlin", false),
        5 to SkillsScreenField(5, 5, "CI/CD", false),
        6 to SkillsScreenField(6, 6, "Frontend", false),
        7 to SkillsScreenField(7, 7, "Backend", false),
        8 to SkillsScreenField(8, 8, "Swift", false),
        9 to SkillsScreenField(9, 9,  "Python", false),
        10 to SkillsScreenField(10, 10, "Java", false),
        11 to SkillsScreenField(11, 11, "IOS", false),
        12 to SkillsScreenField(12, 12,"Ruby", false),
        13 to SkillsScreenField(13, 13,"Machine Learning", false),
        14 to SkillsScreenField(14, 14,"Some data 1", false),
        15 to SkillsScreenField(15, 15, "Some data 2", false),
        16 to SkillsScreenField(16, 16,"Some data 3", false),
        17 to SkillsScreenField(17, 17,"Some data 4", false),
        18 to SkillsScreenField(18, 18,"Some data 5", false),
        19 to SkillsScreenField(19, 19,"Some data 6", false),
        20 to SkillsScreenField(20, 20,"Some data 7", false),
        21 to SkillsScreenField(21, 21,"Some data 8", false),
        22 to SkillsScreenField(22, 22,"Some data 9", false),
        23 to SkillsScreenField(23, 23,"Some data 10", false),
        24 to SkillsScreenField(24, 24,"Some data 11", false),
        25 to SkillsScreenField(25, 25,"Some data 12", false),
        26 to SkillsScreenField(26, 26,"Some data 13", false),
        27 to SkillsScreenField(27, 27,"Some data 14", false),
    )

    fun getResult(): List<SkillsScreenField>? {
        return displayableDataList
    }

    fun refreshDisplayableList(): ArrayList<SkillsScreenField> {
        if (displayableDataList.size != 12) {
            for (i in 0 until displayableDataList.size){
                nonAddedSkillsMap[displayableDataList[i].mapKey]?.isAdded = false
            }
            displayableDataList.clear()
            while (displayableDataList.size != 12) {
                if (nonAddedSkillsMap.containsKey(counter) && nonAddedSkillsMap[counter]?.isAdded != true) {
                    nonAddedSkillsMap[counter]?.isAdded = true
                    nonAddedSkillsMap[counter]?.let { displayableDataList.add(it) }
                }
                if (counter == nonAddedSkillsMap.lastKey()){
                    counter = 0
                }
                else counter++
            }
        }
        counter = 0
        searchFlag = false
        return displayableDataList
    }


    fun addData(skillId: Int) {
        for (i in 0 until displayableDataList.size)
            if (displayableDataList[i].id == skillId) {
                nonAddedSkillsMap[displayableDataList[i].mapKey]?.isAdded = true
                chosenSkillsDataList.add(displayableDataList[i])
                displayableDataList.removeAt(i)
                break
            }
        if (displayableDataList.size == 11 && !searchFlag) {
            while (displayableDataList.size != 12) {
                if (nonAddedSkillsMap.containsKey(counter) && nonAddedSkillsMap[counter]?.isAdded != true) {
                    nonAddedSkillsMap[counter]?.isAdded = true
                    nonAddedSkillsMap[counter]?.let { displayableDataList.add(it) }
                }
                if (counter == nonAddedSkillsMap.lastKey()){
                    counter = 0
                }
                else counter++
            }
        }
    }

    fun searchItem(query: String): ArrayList<SkillsScreenField> {
        val temp = ArrayList<SkillsScreenField>()
        for (i in 0 until displayableDataList.size){
            if(displayableDataList[i].title.contains((query))){
               temp.add(displayableDataList[i])
            }
            else {
                nonAddedSkillsMap[displayableDataList[i].mapKey]?.isAdded = false
            }
        }
        displayableDataList.clear()
        displayableDataList.addAll(temp)
        val iter = nonAddedSkillsMap.iterator()
        while(iter.hasNext()){
            val item = iter.next().component2()
            if (item.title.contains(query) && !item.isAdded){
                displayableDataList.add(item)
            }
        }
        searchFlag = true
        return displayableDataList
    }

    fun getNewData(): List<SkillsScreenField>? {
        return chosenSkillsDataList
    }

    fun removeData(skillId: Int) {
        for (i in 0 until chosenSkillsDataList.size)
            if (chosenSkillsDataList[i].id == skillId) {
                if (displayableDataList.size < 12) displayableDataList.add(chosenSkillsDataList[i])
                else {
                    nonAddedSkillsMap[chosenSkillsDataList[i].mapKey]?.isAdded = false
                }
                chosenSkillsDataList.removeAt(i)
                break
            }
    }
}
