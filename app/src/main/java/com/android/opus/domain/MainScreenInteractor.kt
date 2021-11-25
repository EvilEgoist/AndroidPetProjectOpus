package com.android.opus.domain

import com.android.opus.utils.CheckCorrectUserData
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class MainScreenInteractor(private val dispatcher: CoroutineDispatcher) {
    suspend fun login(
        email: String,
        password: String
    ): RequestResult = withContext(dispatcher) {
        when {
            !CheckCorrectUserData.isValidEmail(email) -> RequestResult.Error.Email()
            !CheckCorrectUserData.isValidPassword(password) -> RequestResult.Error.Password()
            else -> RequestResult.Success.Login()
        }
    }

    suspend fun signUp(
        name: String,
        surname: String,
        email: String,
        firstPassword: String,
        secondPassword: String
    ): RequestResult = withContext(dispatcher) {
        when {
            !CheckCorrectUserData.isValidUserName(name) -> RequestResult.Error.Name()
            !CheckCorrectUserData.isValidUserName(surname) -> RequestResult.Error.Surname()
            !CheckCorrectUserData.isValidEmail(email) -> RequestResult.Error.Email()
            !CheckCorrectUserData.isValidPassword(firstPassword) -> RequestResult.Error.Password()
            !CheckCorrectUserData.isConfirmedPassword(
                firstPassword,
                secondPassword
            ) -> RequestResult.Error.ConfirmedPassword()
            else -> RequestResult.Success.SignUp()
        }
    }

    suspend fun nameCheck(name: String): RequestResult = withContext(dispatcher) {
        when {
            !CheckCorrectUserData.isValidUserName(name) -> RequestResult.Error.Name()
            else -> RequestResult.Success.Name()
        }
    }

    suspend fun surnameCheck(surname: String) : RequestResult = withContext(dispatcher) {
        when {
            !CheckCorrectUserData.isValidUserName(surname) -> RequestResult.Error.Surname()
            else -> RequestResult.Success.Surname()
        }
    }

    suspend fun emailCheck(email: String) : RequestResult= withContext(dispatcher) {
        when {
            !CheckCorrectUserData.isValidEmail(email) -> RequestResult.Error.Email()
            else -> RequestResult.Success.Email()
        }
    }

    suspend fun passwordCheck(password: String) : RequestResult= withContext(dispatcher) {
        when {
            !CheckCorrectUserData.isValidPassword(password) -> RequestResult.Error.Password()
            else -> RequestResult.Success.Password()
        }
    }

    suspend fun passwordConfirmCheck(
        firstPassword: String,
        secondPassword: String
    ): RequestResult = withContext(dispatcher) {
        when {
            !CheckCorrectUserData.isConfirmedPassword(
                firstPassword,
                secondPassword
            ) -> RequestResult.Error.ConfirmedPassword()
            else -> RequestResult.Success.ConfirmedPassword()
        }
    }
}

