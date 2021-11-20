package com.android.opus.domain

sealed class EditProfileResult {

    sealed class Success : EditProfileResult() {
        object SuccessBaseUserInput : Success()
    }

    sealed class Error : EditProfileResult() {
        object Name : Error()
        object Surname : Error()
        object Patronymic : Error()
    }
}
