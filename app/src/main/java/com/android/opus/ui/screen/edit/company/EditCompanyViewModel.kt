package com.android.opus.ui.screen.edit.company

import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.opus.common.adapters.vacancy.edit.VacancyDisplayableItem
import com.android.opus.common.pickers.ImageCropper
import com.android.opus.common.pickers.ImagePicker
import com.android.opus.domain.CompanyProfileInteractor
import com.android.opus.domain.SkillsInteractor
import com.android.opus.model.*
import kotlinx.coroutines.launch

class EditCompanyViewModel(
    private val companyInteractor: CompanyProfileInteractor,
    private val skillsInteractor: SkillsInteractor,
    private val imagePicker: ImagePicker,
    private val imageCropper: ImageCropper
) : ViewModel() {

    val image = MutableLiveData<Uri?>()
    val company = MutableLiveData<CompanyProfile>()
    val companyErrorName = MutableLiveData<Boolean>()
    val editVacancies = MutableLiveData<List<VacancyDisplayableItem>?>()
    private var loadedSkillsVacancy: List<Skill> = listOf()

    init {
        companyErrorName.postValue(false)
        loadSkills()
        loadCompanyProfile()
        addEmptyVacancy()
    }

    fun pickImage() {
        viewModelScope.launch {
            imagePicker.pickSingleImage()
        }
    }

    fun cropImage() {
        viewModelScope.launch {
            imageCropper.cropRoundImage(imagePicker.getSingleImage())
        }
    }

    fun loadPickedImageIntoImagePicker(data: Intent?) {
        imagePicker.loadData(data)
    }

    fun loadCroppedImage(data: Intent?) {
        val imageUri = imageCropper.parseData(data)
        if (imageUri != null && imageUri.toString().isNotEmpty()) {
            company.value?.imageUrl = ""
            company.postValue(company.value)
        }
        image.postValue(imageUri)
    }

    fun getImageRequestCode() = ImagePicker.REQUEST_CODE_CHOOSE

    fun getImageCropCode() = ImageCropper.REQUEST_CODE_CROP

    fun deletePhoto() {
        viewModelScope.launch {
            image.postValue(Uri.parse(""))
            company.value?.imageUrl = ""
            company.postValue(company.value)
        }
    }

    fun loadVacancyFields(
        companyName: String,
        location: String,
        shortDescription: String,
        fullDescription: String
    ) {
        viewModelScope.launch {
            if (companyName.isEmpty()) {
                companyErrorName.postValue(true)
            } else {
                company.value?.companyName = companyName
                company.value?.companyLocation = location
                company.value?.shortCompanyDescr = shortDescription
                company.value?.companyFullDescription = fullDescription
                companyErrorName.postValue(false)
            }
            company.postValue(company.value)
        }
    }

    fun saveCompany() {
        viewModelScope.launch {
            if (companyErrorName.value == false) {
                val vacancies = mutableListOf<ExpandedVacancy>()
                for (i in editVacancies.value!!) {
                    if (i is EditVacancy && i.containsMainInfo()) {
                        vacancies.add(i.vacancy)
                    } else {
                        vacancies.add(i as ExpandedVacancy)
                    }
                }
                company.value?.vacancies = vacancies
                company.postValue(company.value)
            }
        }
    }


    fun addEmptyVacancy() {
        viewModelScope.launch {
            var vacancies = editVacancies.value?.toMutableList()
            if (vacancies == null) {
                vacancies = mutableListOf()
            }
            vacancies.add(
                EditVacancy(
                    ExpandedVacancy(
                        id = vacancies.size + 1,
                        titleVacancy = "",
                        employerName = "",
                        salary = "",
                        description = "",
                        workFullDay = true,
                        level = "",
                        skills = listOf(),
                        companyDescr = "",
                        location = "",
                        typeOfEmployment = "",
                        responsibilities = listOf(
                            ShortDescription(
                                0,
                                ""
                            )
                        ),
                        requirements = listOf(
                            ShortDescription(
                                0,
                                ""
                            )
                        ),
                        conditions = listOf(
                            ShortDescription(
                                0,
                                ""
                            )
                        )
                    ),
                    loadedSkillsVacancy
                )
            )
            editVacancies.postValue(vacancies)
        }
    }

    fun makeVacancyEditable(id: Int) {
        viewModelScope.launch {
            val vacancies = editVacancies.value?.toMutableList()
            val element = vacancies?.get(id)
            vacancies?.set(
                id, EditVacancy(
                    element as ExpandedVacancy,
                    loadedSkillsVacancy
                )
            )
            editVacancies.postValue(vacancies)
        }
    }

    private fun loadSkills() {
        viewModelScope.launch {
            loadedSkillsVacancy = skillsInteractor.loadSkills()
        }
    }

    private fun loadCompanyProfile() {
        viewModelScope.launch {
            val companyRaw = companyInteractor.loadCompanyProfile()
            company.postValue(companyRaw)
            editVacancies.postValue(companyRaw.vacancies)
        }
    }
}
