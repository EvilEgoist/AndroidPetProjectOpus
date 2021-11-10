package com.android.opus.domain

import com.android.opus.model.SkillsScreenField

object SkillsScreenFacade{
    private val chosenSkillsDataList = ArrayList<SkillsScreenField>()
    private var counter = 0
    private var searchFlag = false
    private val displayableDataList = ArrayList<SkillsScreenField>()
    private var addedSkillsMap = sortedMapOf<Int, SkillsScreenField>()
    private var nonAddedSkillsMap = arrayListOf<SkillsScreenField>(
        SkillsScreenField(0, 0,"android"),
        SkillsScreenField(1, 1,"C++"),
        SkillsScreenField(2, 2,"MVVM"),
        SkillsScreenField(3, 3, "C#"),
        SkillsScreenField(4, 4, "Kotlin"),
        SkillsScreenField(5, 5, "CI/CD"),
        SkillsScreenField(6, 6, "Frontend"),
        SkillsScreenField(7, 7, "Backend"),
        SkillsScreenField(8, 8, "Swift"),
        SkillsScreenField(9, 9,  "Python"),
        SkillsScreenField(10, 10, "Java"),
        SkillsScreenField(11, 11, "IOS"),
        SkillsScreenField(12, 12,"Ruby"),
        SkillsScreenField(13, 13,"Machine Learning"),
        SkillsScreenField(14, 14,"Some data 1"),
        SkillsScreenField(15, 15, "Some data 2"),
        SkillsScreenField(16, 16,"Some data 3"),
        SkillsScreenField(17, 17,"Some data 4"),
        SkillsScreenField(18, 18,"Some data 5"),
        SkillsScreenField(19, 19,"Some data 6"),
        SkillsScreenField(20, 20,"Some data 7"),
        SkillsScreenField(21, 21,"Some data 8"),
        SkillsScreenField(22, 22,"Some data 9"),
        SkillsScreenField(23, 23,"Some data 10"),
        SkillsScreenField(24, 24,"Some data 11"),
        SkillsScreenField(25, 25,"Some data 12"),
        SkillsScreenField(26, 26,"Some data 13"),
        SkillsScreenField(27, 27,"Some data 14"),
    )

    init {
        refreshDisplayableList()
    }

    fun getResult(): List<SkillsScreenField>? {
        return displayableDataList
    }

    fun refreshDisplayableList(): ArrayList<SkillsScreenField> {
        if (displayableDataList.size != 12) {
            for (i in 0 until displayableDataList.size) {
                addedSkillsMap.remove(displayableDataList[i].mapKey)
                nonAddedSkillsMap.add(displayableDataList[i])
            }
            displayableDataList.clear()
            var i = 0
            while (displayableDataList.size != 12 && nonAddedSkillsMap.isNotEmpty()) {
                nonAddedSkillsMap[0].mapKey = addedSkillsMap.size
                addedSkillsMap[addedSkillsMap.size] = nonAddedSkillsMap[0]
                displayableDataList.add(nonAddedSkillsMap[0])
                nonAddedSkillsMap.removeAt(0)
            }
        }
        searchFlag = false
        return displayableDataList
    }


    fun addData(skillId: Int):ArrayList<SkillsScreenField> {
        for (i in 0 until displayableDataList.size)
            if (displayableDataList[i].id == skillId) {
                chosenSkillsDataList.add(displayableDataList[i])
                displayableDataList.removeAt(i)
                break
            }
        if (displayableDataList.size == 11 && !searchFlag && nonAddedSkillsMap.isNotEmpty()) {
            displayableDataList.add(nonAddedSkillsMap[0])
            nonAddedSkillsMap[0].mapKey = addedSkillsMap.size
            addedSkillsMap[addedSkillsMap.size] = nonAddedSkillsMap[0]
            nonAddedSkillsMap.removeAt(0)
        }
        return displayableDataList
    }

    fun searchItem(query: String): ArrayList<SkillsScreenField> {
        val temp = ArrayList<SkillsScreenField>()
        var counter = 0
        for (i in 0 until displayableDataList.size){
            if(displayableDataList[i].title.contains((query))){
               temp.add(displayableDataList[i])
            }
            else {
                nonAddedSkillsMap.add(counter, displayableDataList[i])
                addedSkillsMap.remove(displayableDataList[i].mapKey)
                counter++
            }
        }
        displayableDataList.clear()
        displayableDataList.addAll(temp)
        var i = 0
        while (i < nonAddedSkillsMap.size){
            if (nonAddedSkillsMap[i].title.contains(query)){
                nonAddedSkillsMap[i].mapKey = addedSkillsMap.size
                addedSkillsMap[addedSkillsMap.size] = nonAddedSkillsMap[i]
                displayableDataList.add(nonAddedSkillsMap[i])
                nonAddedSkillsMap.removeAt(i)
            }
            i++
        }
        searchFlag = true
        return displayableDataList
    }

    fun getNewData(): List<SkillsScreenField>? {
        return chosenSkillsDataList
    }

    fun removeData(skillId: Int): ArrayList<SkillsScreenField> {
        for (i in 0 until chosenSkillsDataList.size)
            if (chosenSkillsDataList[i].id == skillId) {
                if (displayableDataList.size < 12) {
                    displayableDataList.add(chosenSkillsDataList[i])
                }
                else {
                    addedSkillsMap.remove(chosenSkillsDataList[i].mapKey)
                    nonAddedSkillsMap.add(chosenSkillsDataList[i])
                }
                chosenSkillsDataList.removeAt(i)
                break
            }
        return chosenSkillsDataList
    }
}
