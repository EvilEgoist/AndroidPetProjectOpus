package com.android.opus.ui.screen.edit.create.company

import android.content.Intent
import android.net.Uri
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.opus.common.pickers.ImageCropper
import com.android.opus.common.pickers.ImagePicker
import com.android.opus.model.*
import kotlinx.coroutines.launch

class CreateCompanyViewModel(
    private val imagePicker: ImagePicker,
    private val imageCropper: ImageCropper
) : ViewModel() {

    val image = MutableLiveData<Uri?>()
    val company = MutableLiveData<CompanyProfile>()
    val companyErrorName = MutableLiveData<Boolean>()

    init {
        companyErrorName.postValue(false)
        company.postValue(
            CompanyProfile(
                123,
                "",
                "",
                "",
                "",
                "",
                listOf<ExpandedVacancy>()
            )
        )
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

    fun loadCompanyFields(
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
                company.postValue(company.value)
            }
        }
    }
}