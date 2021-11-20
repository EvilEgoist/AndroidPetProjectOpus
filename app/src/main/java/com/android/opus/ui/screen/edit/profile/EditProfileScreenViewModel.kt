package com.android.opus.ui.screen.edit.profile

import android.content.Intent
import android.net.Uri
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.opus.common.adapters.work.place.WorkPlaceDisplayableItem
import com.android.opus.common.pickers.ImageCropper
import com.android.opus.common.pickers.ImagePicker
import com.android.opus.domain.EditProfileResult
import com.android.opus.domain.ProfileInteractor
import com.android.opus.domain.SkillsInteractor
import com.android.opus.model.*
import com.android.opus.ui.screen.edit.CheckBaseProfileInfo
import com.android.opus.ui.screen.edit.empty.profile.EditEmptyProfileScreenViewModel
import kotlinx.coroutines.launch

class EditProfileScreenViewModel(
    private val skillsInteractor: SkillsInteractor,
    private val profileInteractor: ProfileInteractor,
    private val imagePicker: ImagePicker,
    private val imageCropper: ImageCropper
) : ViewModel() {

    val chosenSkills = MutableLiveData<List<Skill>?>()
    val loadedSkills = MutableLiveData<List<Skill>?>()
    val editWorkExperience = MutableLiveData<List<WorkPlaceDisplayableItem>?>()
    val profile = MutableLiveData<Profile>()
    val editSelfDescriptionState = MutableLiveData<Boolean>()
    val image = MutableLiveData<Uri>()
    val userBaseDataInput = MutableLiveData<EditProfileResult>()
    private var loadedSkillsEditWorkPlace: List<Skill> = listOf()

    init {
        editSelfDescriptionState.postValue(false)
        loadProfile()
        loadSkills()
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
            profile.value?.imageUrl = ""
            profile.postValue(profile.value)
        }
        image.postValue(imageUri)
    }

    fun getImageRequestCode() = ImagePicker.REQUEST_CODE_CHOOSE

    fun getImageCropCode() = ImageCropper.REQUEST_CODE_CROP

    fun deletePhoto() {
        viewModelScope.launch {
            image.postValue(Uri.parse(""))
            profile.value?.imageUrl = ""
            profile.postValue(profile.value)
        }
    }

    fun loadProfileFields(
        name: String,
        surname: String,
        patronymic: String,
        birthDay: String,
        birthMonth: String,
        birthYear: String,
        shortSelfDescription: String,
        startSalary: String,
        endSalary: String,
        selfDescription: String
    ) {
        val result = CheckBaseProfileInfo.checkCorrectBaseProfileEdit(
            name,
            surname,
            patronymic
        )
        if (result == EditProfileResult.Success.SuccessBaseUserInput) {
            profile.value?.firstName = name
            profile.value?.secondName = surname
            profile.value?.patronymic = patronymic
            profile.value?.birthDate =
                birthDay + EditEmptyProfileScreenViewModel.BIRTH_DELIM + birthMonth + EditEmptyProfileScreenViewModel.BIRTH_DELIM + birthYear
            profile.value?.shortSelfDescription = shortSelfDescription
            profile.value?.salaryStart = startSalary
            profile.value?.salaryEnd = endSalary
            profile.value?.selfDescription = selfDescription
            profile.postValue(profile.value)
        }
        userBaseDataInput.postValue(result)
    }

    fun saveProfile() {
        viewModelScope.launch {
            if (userBaseDataInput.value == EditProfileResult.Success.SuccessBaseUserInput) {
                val workExperience: MutableList<WorkPlace> = mutableListOf()
                for (i in editWorkExperience.value!!) {
                    if (i is EditWorkPlace) {
                        workExperience.add(i.workPlace)
                    } else {
                        workExperience.add(i as WorkPlace)
                    }
                }
                profile.value?.workExperience = workExperience
                profile.postValue(profile.value)
            }
        }
    }

    fun addSkillToChosen(id: Int) {
        viewModelScope.launch {
            val listLoadedSkills: MutableList<Skill>? = loadedSkills.value?.toMutableList()
            var listChosenSkills: MutableList<Skill>? = chosenSkills.value?.toMutableList()
            val item = listLoadedSkills?.find {
                it.id == id
            }
            listLoadedSkills?.remove(item)
            if (listChosenSkills == null && item != null) {
                listChosenSkills = mutableListOf()
            }
            item?.let {
                listChosenSkills?.add(it)
            }
            loadedSkills.postValue(listLoadedSkills)
            chosenSkills.postValue(listChosenSkills)
        }
    }

    fun replaceSkillFromChosen(id: Int) {
        viewModelScope.launch {
            val listLoadedSkills: MutableList<Skill>? = loadedSkills.value?.toMutableList()
            val listChosenSkills: MutableList<Skill>? = chosenSkills.value?.toMutableList()
            val item = listChosenSkills?.find {
                it.id == id
            }
            listChosenSkills?.remove(item)
            item?.let {
                listLoadedSkills?.add(it)
            }
            chosenSkills.postValue(listChosenSkills)
            loadedSkills.postValue(listLoadedSkills)
        }
    }

    fun addEmptyEditWorkPlace() {
        viewModelScope.launch {
            var listEmptyEditWorkPlace = editWorkExperience.value?.toMutableList()
            if (listEmptyEditWorkPlace == null) {
                listEmptyEditWorkPlace = mutableListOf()
            }
            listEmptyEditWorkPlace.add(
                EditWorkPlace(
                    WorkPlace(
                        id = listEmptyEditWorkPlace.size + 1,
                        companyName = "",
                        workPosition = "",
                        monthStartWork = "",
                        yearStartWork = "",
                        monthEndWork = "",
                        yearEndWork = "",
                        isWorkCurrentTime = false,
                        responsibilities = "",
                        skills = listOf()
                    ),
                    loadedSkills = loadedSkillsEditWorkPlace
                )
            )
            editWorkExperience.postValue(listEmptyEditWorkPlace)
        }
    }

    fun updateEditShortDescriptionState() = if (editSelfDescriptionState.value == false) {
        editSelfDescriptionState.postValue(true)
    } else {
        editSelfDescriptionState.postValue(false)
    }

    fun setSelfDescription(selfDescription: String) {
        viewModelScope.launch {
            profile.value?.selfDescription = selfDescription
            profile.postValue(profile.value)
        }
    }

    fun makeWorkPlaceEditable(id: Int) {
        viewModelScope.launch {
            val workPlaces = editWorkExperience.value?.toMutableList()
            val element = workPlaces?.get(id)
            workPlaces?.set(
                id, EditWorkPlace(
                    element as WorkPlace,
                    loadedSkillsEditWorkPlace
                )
            )
            editWorkExperience.postValue(workPlaces)
        }
    }

    fun setChosenLevel(level: String) {
        profile.value?.level = level
        profile.postValue(profile.value)
    }

    private fun loadSkills() {
        viewModelScope.launch {
            loadedSkills.postValue(skillsInteractor.loadSkills())
            loadedSkillsEditWorkPlace = skillsInteractor.loadSkills()
        }
    }

    private fun loadProfile() {
        viewModelScope.launch {
            val profileToLoad = profileInteractor.loadMainInfo()
            profile.postValue(profileToLoad)
            editWorkExperience.postValue(profileToLoad.workExperience)
        }
    }
}
