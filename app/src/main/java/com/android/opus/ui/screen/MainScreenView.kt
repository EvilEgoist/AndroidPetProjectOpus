package com.android.opus.ui.screen

interface MainScreenView {
    fun showNameError()
    fun showSurnameError()
    fun showEmailError()
    fun showPasswordError()
    fun showConfirmError()
    fun showConfirmSuccess()
    fun clearErrorsInForms()
}
