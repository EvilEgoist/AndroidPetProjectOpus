package com.android.opus.model

data class Profile(
    val id: Int,
    val imageUrl: String?,
    val username: String,
    val age: String,
    val experience: String,
    val level: String,
    val status: String,
    val selfDescription: String?,
    val skills: List<Skill>?,
    val workExperience: List<WorkPlace>?
)
