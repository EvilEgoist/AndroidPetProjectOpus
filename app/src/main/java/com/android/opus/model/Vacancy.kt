package com.android.opus.model

import com.android.opus.common.DisplayableItem
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Vacancy(
    val id: Int,
    val titleVacancy: String,
    val employerName: String,
    val salary: String?,
    val description: String?,
    val imageUrl: String?,
    val skills: List<Skill>
) : DisplayableItem