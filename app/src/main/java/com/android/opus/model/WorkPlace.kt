package com.android.opus.model

data class WorkPlace(
    val id: Int,
    val imageUrl: String?,
    val title: String,
    val placeDescription: String,
    val jobPosition: String,
    val workPeriod: String,
    val responsibilities: String?,
    val skills: List<Skill>?
)
