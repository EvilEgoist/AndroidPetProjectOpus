package com.android.opus.ui.screen.edit.profile

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.RadioButton
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.opus.R
import com.android.opus.common.adapters.skills.ChosenSkillsAdapter
import com.android.opus.common.adapters.skills.SkillsAdapter
import com.android.opus.common.adapters.work.place.WorkPlaceDelegateAdapter
import com.android.opus.common.adapters.work.place.WorkPlaceDisplayableItem
import com.android.opus.common.pickers.ImageCropper
import com.android.opus.common.pickers.ImagePicker
import com.android.opus.domain.EditProfileResult
import com.android.opus.domain.ProfileInteractor
import com.android.opus.domain.SkillsInteractor
import com.android.opus.model.Profile
import com.android.opus.model.Skill
import com.beloo.widget.chipslayoutmanager.ChipsLayoutManager
import com.beloo.widget.chipslayoutmanager.SpacingItemDecoration
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.fragment_edit_profile.*
import kotlinx.android.synthetic.main.fragment_edit_profile.add_new_work_place
import kotlinx.android.synthetic.main.fragment_edit_profile.delete_photo
import kotlinx.android.synthetic.main.fragment_edit_profile.edit_birth_day
import kotlinx.android.synthetic.main.fragment_edit_profile.edit_birth_month
import kotlinx.android.synthetic.main.fragment_edit_profile.edit_birth_year
import kotlinx.android.synthetic.main.fragment_edit_profile.edit_first_name
import kotlinx.android.synthetic.main.fragment_edit_profile.edit_patronymic
import kotlinx.android.synthetic.main.fragment_edit_profile.edit_photo
import kotlinx.android.synthetic.main.fragment_edit_profile.edit_profile_chosen_skills
import kotlinx.android.synthetic.main.fragment_edit_profile.edit_profile_loaded_skills
import kotlinx.android.synthetic.main.fragment_edit_profile.edit_save
import kotlinx.android.synthetic.main.fragment_edit_profile.edit_second_name
import kotlinx.android.synthetic.main.fragment_edit_profile.edit_self_description
import kotlinx.android.synthetic.main.fragment_edit_profile.edit_short_self_description
import kotlinx.android.synthetic.main.fragment_edit_profile.end_salary
import kotlinx.android.synthetic.main.fragment_edit_profile.profile_photo
import kotlinx.android.synthetic.main.fragment_edit_profile.radio_junior
import kotlinx.android.synthetic.main.fragment_edit_profile.radio_level_group
import kotlinx.android.synthetic.main.fragment_edit_profile.radio_middle
import kotlinx.android.synthetic.main.fragment_edit_profile.radio_senior
import kotlinx.android.synthetic.main.fragment_edit_profile.start_salary
import kotlinx.coroutines.Dispatchers

class EditProfileScreenFragment : Fragment(R.layout.fragment_edit_profile) {

    private val viewModel = EditProfileScreenViewModel(
        skillsInteractor = SkillsInteractor(Dispatchers.Default),
        profileInteractor = ProfileInteractor(Dispatchers.Default),
        imagePicker = ImagePicker(this, Dispatchers.Default),
        imageCropper = ImageCropper(this, Dispatchers.Default)
    )

    private val skillsAdapter = SkillsAdapter { id ->
        viewModel.addSkillToChosen(id)

    }
    private val chosenSkillAdapter = ChosenSkillsAdapter { id ->
        viewModel.replaceSkillFromChosen(id)
    }

    private val workPlaceAdapter = WorkPlaceDelegateAdapter { id ->
        viewModel.makeWorkPlaceEditable(id)
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
        add_new_work_place.setOnClickListener {
            viewModel.addEmptyEditWorkPlace()
        }
        edit_about_self_info.setOnClickListener {
            viewModel.updateEditShortDescriptionState()
        }
        edit_save.setOnClickListener {
            viewModel.loadProfileFields(
                edit_first_name.text.toString(),
                edit_second_name.text.toString(),
                edit_patronymic.text.toString(),
                edit_birth_day.text.toString(),
                edit_birth_month.text.toString(),
                edit_birth_year.text.toString(),
                edit_short_self_description.text.toString(),
                start_salary.text.toString(),
                end_salary.text.toString(),
                edit_self_description.text.toString()
            )
            viewModel.saveProfile()
        }
        radio_level_group.setOnCheckedChangeListener { _, checkedId ->
            view.findViewById<RadioButton>(checkedId)?.apply {
                viewModel.setChosenLevel(text.toString())
            }
        }

        setUpLoadedSkillsAdapter()
        setUpChosenSkillsAdapter()
        setUpWorkPlaceAdapter()

        viewModel.chosenSkills.observe(this.viewLifecycleOwner, this::updateChosenSkillsAdapter)
        viewModel.loadedSkills.observe(this.viewLifecycleOwner, this::updateLoadedSkillsAdapter)
        viewModel.editWorkExperience.observe(
            this.viewLifecycleOwner,
            this::updateEditWorkPlaceAdapter
        )
        viewModel.editSelfDescriptionState.observe(
            this.viewLifecycleOwner,
            this::updateSelfDescription
        )
        viewModel.profile.observe(this.viewLifecycleOwner, this::updateProfile)
        viewModel.image.observe(this.viewLifecycleOwner, this::updateImage)
        viewModel.userBaseDataInput.observe(this.viewLifecycleOwner, this::updateProfileInputResult)
    }

    private fun updateProfileInputResult(result: EditProfileResult) {
        when (result) {
            EditProfileResult.Error.Name -> edit_first_name.error = ""
            EditProfileResult.Error.Surname -> edit_second_name.error = " "
            EditProfileResult.Error.Patronymic -> edit_patronymic.error = " "
        }
    }


    private fun setUpLoadedSkillsAdapter() {
        edit_profile_loaded_skills?.layoutManager = ChipsLayoutManager.newBuilder(requireContext())
            .build()
        edit_profile_loaded_skills?.adapter = skillsAdapter
        edit_profile_loaded_skills?.addItemDecoration(SpacingItemDecoration(8, 2))
    }

    private fun setUpChosenSkillsAdapter() {
        edit_profile_chosen_skills?.layoutManager = ChipsLayoutManager.newBuilder(requireContext())
            .build()
        edit_profile_chosen_skills?.adapter = chosenSkillAdapter
        edit_profile_chosen_skills?.addItemDecoration(SpacingItemDecoration(8, 2))
    }

    private fun setUpWorkPlaceAdapter() {
        work_experience?.layoutManager = LinearLayoutManager(requireContext())
        work_experience?.adapter = workPlaceAdapter
    }

    private fun updateChosenSkillsAdapter(chosenSkills: List<Skill>?) {
        chosenSkillAdapter.submitList(chosenSkills)
    }

    private fun updateLoadedSkillsAdapter(skills: List<Skill>?) {
        skillsAdapter.submitList(skills)
    }

    private fun updateEditWorkPlaceAdapter(workPlaces: List<WorkPlaceDisplayableItem>?) {
        if (workPlaces != null) {
            workPlaceAdapter.setData(workPlaces)
        }
    }

    private fun updateSelfDescription(onEditSelfDescription: Boolean) {
        if (onEditSelfDescription) {
            edit_self_description.visibility = View.VISIBLE
            self_description.visibility = View.GONE
        } else {
            viewModel.setSelfDescription(edit_self_description.text.toString())
            edit_self_description.visibility = View.GONE
            self_description.visibility = View.VISIBLE
        }
    }

    private fun updateProfile(profile: Profile) {
        if (profile.imageUrl.isNotEmpty()) {
            Glide.with(this)
                .load(profile.imageUrl)
                .into(profile_photo)
        }
        if (profile.level == JUNIOR_TITLE) {
            radio_junior.isChecked = true
        }
        if (profile.level == MIDDLE_TITLE) {
            radio_middle.isChecked = true
        }
        if (profile.level == SENIOR_TITLE) {
            radio_senior.isChecked = true
        }
        edit_first_name.setText(profile.firstName)
        edit_second_name.setText(profile.secondName)
        edit_patronymic.setText(profile.patronymic)
        edit_short_self_description.setText(profile.shortSelfDescription)
        edit_self_description.setText(profile.selfDescription)
        self_description.text = profile.selfDescription
        start_salary.setText(profile.salaryStart)
        end_salary.setText(profile.salaryEnd)
        formatBirthDate(profile)
    }

    private fun updateImage(imageUri: Uri) {
        Glide.with(this)
            .load(imageUri)
            .placeholder(R.drawable.ic_empty_photo)
            .into(profile_photo)
    }

    private fun formatBirthDate(profile: Profile) {
        val rawBirthDate = profile.birthDate.split("/")
        edit_birth_day.setText(rawBirthDate[0])
        edit_birth_month.setText(rawBirthDate[1])
        edit_birth_year.setText(rawBirthDate[2])
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
        const val JUNIOR_TITLE = "Junior"
        const val MIDDLE_TITLE = "Middle"
        const val SENIOR_TITLE = "Senior"
        fun newInstance(): EditProfileScreenFragment = EditProfileScreenFragment()
    }
}
