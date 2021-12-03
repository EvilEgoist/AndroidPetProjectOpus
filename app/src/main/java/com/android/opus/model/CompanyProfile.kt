package com.android.opus.model

data class CompanyProfile(
    val id: Int,
    var imageUrl: String,
    var companyName: String,
    var companyLocation: String,
    var shortCompanyDescr: String,
    var companyFullDescription: String,
    var vacancies: List<ExpandedVacancy>
)
