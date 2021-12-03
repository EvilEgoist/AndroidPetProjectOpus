package com.android.opus.ui.screen.edit.create.company

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
import com.android.opus.common.pickers.ImageCropper
import com.android.opus.common.pickers.ImagePicker
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.fragment_create_company.*
import kotlinx.coroutines.Dispatchers

class CreateCompanyFragment : Fragment(R.layout.fragment_create_company) {
    private val viewModel =
        CreateCompanyViewModel(
            imagePicker = ImagePicker(this, Dispatchers.Default),
            imageCropper = ImageCropper(this, Dispatchers.Default)
        )

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        edit_photo.setOnClickListener {
            initStoragePermissions()
            if (isStoragePermissionGranted(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                && isStoragePermissionGranted(Manifest.permission.READ_EXTERNAL_STORAGE)
            ) {
                viewModel.pickImage()
            }
        }
        delete_photo.setOnClickListener {
            viewModel.deletePhoto()
        }
        edit_save.setOnClickListener {
            viewModel.loadCompanyFields(
                edit_company_name.text.toString(),
                edit_location.text.toString(),
                edit_short_self_description.text.toString(),
                edit_self_description.text.toString()
            )
            viewModel.saveCompany()
        }
        viewModel.image.observe(this.viewLifecycleOwner, this::updateImage)
        viewModel.companyErrorName.observe(this.viewLifecycleOwner, this::updateErrors)
    }

    private fun updateImage(imageUri: Uri?) {
        Glide.with(this)
            .load(imageUri)
            .placeholder(R.drawable.ic_empty_photo)
            .into(profile_photo)
    }

    private fun updateErrors(errors: Boolean) {
        if (errors) {
            edit_company_name_layout.error = " "
        } else {
            edit_company_name_layout.isErrorEnabled = false
        }
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
        fun newInstance(): CreateCompanyFragment = CreateCompanyFragment()
    }
}
