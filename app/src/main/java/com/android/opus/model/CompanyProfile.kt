package com.android.opus.model

data class CompanyProfile(
    val id: Int,
    val imageUrl: String,
    val companyName: String,
    val companyLocation: String,
    val shortCompanyDescr: String,
    val vacancies: List<ExpandedVacancy>
)
