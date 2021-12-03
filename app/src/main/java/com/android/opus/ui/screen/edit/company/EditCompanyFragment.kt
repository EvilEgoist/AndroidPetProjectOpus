package com.android.opus.ui.screen.edit.company

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
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.opus.R
import com.android.opus.common.adapters.vacancy.edit.EditVacancyDelegateAdapter
import com.android.opus.common.adapters.vacancy.edit.VacancyDisplayableItem
import com.android.opus.common.pickers.ImageCropper
import com.android.opus.common.pickers.ImagePicker
import com.android.opus.domain.CompanyProfileInteractor
import com.android.opus.domain.SkillsInteractor
import com.android.opus.model.CompanyProfile
import com.android.opus.ui.screen.edit.empty.profile.EditEmptyProfileScreenFragment
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.fragment_edit_company.*
import kotlinx.android.synthetic.main.fragment_edit_company.delete_photo
import kotlinx.android.synthetic.main.fragment_edit_company.edit_company_name
import kotlinx.android.synthetic.main.fragment_edit_company.edit_company_name_layout
import kotlinx.android.synthetic.main.fragment_edit_company.edit_location
import kotlinx.android.synthetic.main.fragment_edit_company.edit_photo
import kotlinx.android.synthetic.main.fragment_edit_company.edit_save
import kotlinx.android.synthetic.main.fragment_edit_company.edit_self_description
import kotlinx.android.synthetic.main.fragment_edit_company.edit_short_self_description
import kotlinx.android.synthetic.main.fragment_edit_company.profile_photo
import kotlinx.coroutines.Dispatchers

class EditCompanyFragment : Fragment(R.layout.fragment_edit_company) {

    private val viewModel = EditCompanyViewModel(
        companyInteractor = CompanyProfileInteractor(Dispatchers.Default),
        skillsInteractor = SkillsInteractor(Dispatchers.Default),
        imagePicker = ImagePicker(this, Dispatchers.Default),
        imageCropper = ImageCropper(this, Dispatchers.Default)
    )

    private val companyProfileAdapter: EditVacancyDelegateAdapter =
        EditVacancyDelegateAdapter { id ->
            viewModel.makeVacancyEditable(id)
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
        add_new_vacancy.setOnClickListener {
            viewModel.addEmptyVacancy()
        }
        edit_save.setOnClickListener {
            viewModel.loadVacancyFields(
                edit_company_name.text.toString(),
                edit_location.text.toString(),
                edit_short_self_description.text.toString(),
                edit_self_description.text.toString()
            )
            viewModel.saveCompany()
        }

        setUpVacanciesAdapter()

        viewModel.image.observe(this.viewLifecycleOwner, this::updateImage)
        viewModel.editVacancies.observe(this.viewLifecycleOwner, this::updateVacancies)
        viewModel.companyErrorName.observe(this.viewLifecycleOwner, this::updateErrors)
        viewModel.company.observe(this.viewLifecycleOwner, this::updateCompany)
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

    private fun setUpVacanciesAdapter() {
        edit_vacancies?.layoutManager = LinearLayoutManager(requireContext())
        edit_vacancies?.adapter = companyProfileAdapter
    }

    private fun updateVacancies(vacancies: List<VacancyDisplayableItem>?) {
        if (vacancies != null) {
            companyProfileAdapter.setData(vacancies)
        }
    }

    private fun updateCompany(company: CompanyProfile) {
        if (company.imageUrl.isNotEmpty()) {
            Glide.with(this)
                .load(company.imageUrl)
                .into(profile_photo)
        }
        edit_self_description.text.toString()
        edit_company_name.setText(company.companyName)
        edit_location.setText(company.companyLocation)
        edit_short_self_description.setText(company.shortCompanyDescr)
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
        fun newInstance(): EditCompanyFragment = EditCompanyFragment()
    }
}
