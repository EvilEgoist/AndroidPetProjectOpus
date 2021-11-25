package com.android.opus.domain

sealed class RequestResult {

    sealed class Success : RequestResult() {
        class Login : Success()
        class SignUp : Success()
        class Name : Success()
        class Surname : Success()
        class Email : Success()
        class Password() : Success()
        class ConfirmedPassword() : Success()
    }

    sealed class Error : RequestResult() {
        class Login : Error()
        class SignUp : Error()
        class Name : Error()
        class Surname : Error()
        class Password : Error()
        class Email : Error()
        class ConfirmedPassword : Error()
    }
}
