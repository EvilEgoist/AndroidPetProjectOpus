package com.android.opus.ui.screen.mainphoto

import android.content.Intent
import android.net.Uri
import androidx.fragment.app.Fragment
import com.theartofdev.edmodo.cropper.CropImage
import com.theartofdev.edmodo.cropper.CropImageView
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class ImageCropper(
    private val fragment: Fragment,
    private val dispatcher: CoroutineDispatcher
) {
    suspend fun cropRoundImage(imageUri: Uri?) {
        withContext(dispatcher) {
            CropImage.activity(imageUri)
                .setAspectRatio(1, 1)
                .setCropShape(CropImageView.CropShape.OVAL)
                .start(fragment.requireContext(), fragment)
        }
    }

    fun parseData(data: Intent?) : Uri? {
        return CropImage.getActivityResult(data)?.uri
    }

    companion object {
        const val REQUEST_CODE_CROP = CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE
    }
}
