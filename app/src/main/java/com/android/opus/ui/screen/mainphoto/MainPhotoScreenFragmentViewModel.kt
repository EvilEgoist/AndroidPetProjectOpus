package com.android.opus.ui.screen.mainphoto

import android.content.Intent
import android.net.Uri
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class MainPhotoScreenFragmentViewModel(
    private val imagePicker: ImagePicker,
    private val imageCropper: ImageCropper
) : ViewModel() {

    val imageUri = MutableLiveData<Uri?>()

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
        val image = imageCropper.parseData(data)
        imageUri.postValue(image)
    }

    fun getImageRequestCode() = ImagePicker.REQUEST_CODE_CHOOSE

    fun getImageCropCode() = ImageCropper.REQUEST_CODE_CROP
}
