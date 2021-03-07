package com.android.opus.domain

sealed class RequestResult {

    sealed class Success : RequestResult() {
        class Login : Success()
        class SignUp : Success()
        class ConfirmedPassword() : Success()
    }

    sealed class Error : RequestResult() {
        class Name : Error()
        class Surname : Error()
        class Password : Error()
        class Email : Error()
        class ConfirmedPassword() : Error()
    }
}