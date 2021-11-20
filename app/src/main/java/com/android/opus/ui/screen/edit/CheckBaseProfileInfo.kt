package com.android.opus.ui.screen.edit

import com.android.opus.domain.EditProfileResult
import com.android.opus.utils.CheckCorrectUserData

class CheckBaseProfileInfo {
    companion object {
        fun checkCorrectBaseProfileEdit(
            name: String,
            surname: String,
            patronymic: String
        ): EditProfileResult {
            var patronymicCheckResult = true
            if (patronymic.isNotEmpty()) {
                patronymicCheckResult = CheckCorrectUserData.isValidUserName(patronymic)
            }
            return when {
                !CheckCorrectUserData.isValidUserName(name) -> EditProfileResult.Error.Name
                !CheckCorrectUserData.isValidUserName(surname) -> EditProfileResult.Error.Surname
                !patronymicCheckResult -> EditProfileResult.Error.Patronymic
                else -> EditProfileResult.Success.SuccessBaseUserInput
            }
        }
    }
}