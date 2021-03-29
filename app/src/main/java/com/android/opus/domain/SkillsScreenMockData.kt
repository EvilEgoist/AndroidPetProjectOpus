package com.android.opus.domain

import com.android.opus.model.FieldOfActivity
import com.android.opus.model.SkillsScreenField

object SkillsScreenMockData {
    var chosenSkillsDataList = ArrayList<SkillsScreenField>()
    private var counter = 0
     var mockData = listOf(
        SkillsScreenField(1,"android"),
        SkillsScreenField(2,"C++",),
        SkillsScreenField(3,"MVVM",),
        SkillsScreenField(4,"C#", ),
        SkillsScreenField(5,"Kotlin", ),
        SkillsScreenField(6,"CI/CD", ),
        SkillsScreenField(7,"android"),
        SkillsScreenField(8,"C++",),
        SkillsScreenField(9,"MVVM",),
        SkillsScreenField(10,"C#", ),
        SkillsScreenField(11,"Kotlin", ),
        SkillsScreenField(12,"CI/CD", ),
    )

    fun getResult(): List<SkillsScreenField>? {
        return mockData
    }

    fun addData (position:Int){
        chosenSkillsDataList.add(SkillsScreenField(counter, mockData[position-1].title))
        ++counter
    }

    fun getNewData():List<SkillsScreenField>?{
        return chosenSkillsDataList
    }

    fun removeData(position: Int){
        chosenSkillsDataList.removeAt(position-1)
    }

//    fun getMockData(position:Int):String {
//        return mockData[position].title
//    }
}
