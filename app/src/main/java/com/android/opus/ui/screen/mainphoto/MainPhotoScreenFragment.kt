package com.android.opus.ui.screen.mainphoto

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.observe
import com.android.opus.R
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.fragment_main_photo_screen.*
import kotlinx.coroutines.Dispatchers

class MainPhotoScreenFragment : Fragment(R.layout.fragment_main_photo_screen) {

    private var viewModel: MainPhotoScreenFragmentViewModel = MainPhotoScreenFragmentViewModel(
        ImagePicker(this, Dispatchers.Default),
        ImageCropper(this,  Dispatchers.Default)
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        photo.setOnClickListener {
            initStoragePermissions()
            if (isStoragePermissionGranted(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                && isStoragePermissionGranted(Manifest.permission.READ_EXTERNAL_STORAGE)
            ) {
                viewModel.pickImage()
            }
        }
        viewModel.imageUri.observe(this.viewLifecycleOwner, this::updateImage)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == viewModel.getImageRequestCode() && resultCode == Activity.RESULT_OK) {
            viewModel.loadPickedImageIntoImagePicker(data)
            viewModel.cropImage()
        }
        if (requestCode == viewModel.getImageCropCode() && resultCode == Activity.RESULT_OK) {
            viewModel.loadCroppedImage(data)
        }
    }

    private fun updateImage(imageUri: Uri?) {
        Glide.with(this).load(imageUri).into(photo)
    }

    private fun initStoragePermissions() {
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            requestPermissions(
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE
            )
        }
    }

    private fun isStoragePermissionGranted(permission: String): Boolean =
        ActivityCompat.checkSelfPermission(
            requireActivity(),
            permission
        ) == PackageManager.PERMISSION_GRANTED

    private fun requestPermissions(vararg permissions: String?) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val list: MutableList<String?> = ArrayList()
            for (i in permissions.indices) {
                if (ContextCompat.checkSelfPermission(
                        requireContext(),
                        permissions[i]!!
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    list.add(permissions[i])
                }
            }
            ActivityCompat.requestPermissions(requireActivity(), list.toTypedArray(), 0)
        }
    }

    companion object {
        fun newInstance(): MainPhotoScreenFragment = MainPhotoScreenFragment()
    }
}
