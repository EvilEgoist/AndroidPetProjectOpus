package com.android.opus.ui.screen.mainscreen

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.opus.domain.MainScreenInteractor
import com.android.opus.domain.RequestResult
import com.android.opus.ui.screen.mainscreen.MainScreenViewModel.State.*
import kotlinx.coroutines.launch

class MainScreenViewModel(
    private val interactor: MainScreenInteractor
) : ViewModel() {

    val state = MutableLiveData<State>()

    private val name = MutableLiveData<String>()
    private val surname = MutableLiveData<String>()
    private val email = MutableLiveData<String>()
    private val firstPassword = MutableLiveData<String>()
    private val secondPassword = MutableLiveData<String>()

    init {
        state.postValue(MAIN)
    }

    private fun login() {
        viewModelScope.launch {
            val loginResult = interactor.login(
                email = email.value.toString(),
                password = firstPassword.value.toString()
            )
        }
    }

    private fun signUp() {
        viewModelScope.launch {
            val signUpResult = interactor.signUp(
                name = name.value.toString(),
                surname = surname.value.toString(),
                email = email.value.toString(),
                firstPassword = firstPassword.value.toString(),
                secondPassword = secondPassword.value.toString()
            )
        }
    }

    private fun signInWithGoogl() {}

    private fun signUpWithGoogl() {}

    private fun setSignUpState() {
        state.postValue(SIGN_UP)
    }

    private fun setLoginState() {
        state.postValue(LOGIN)
    }

    fun enterBtnClicked() {
        when (state.value) {
            MAIN -> {
                setLoginState()
            }
            LOGIN -> {
                state.postValue(LOADER)
                login()
            }
            SIGN_UP -> {
                state.postValue(LOADER)
                signUp()
            }
        }
    }

    fun signBtnClicked() {
        when (state.value) {
            MAIN -> {
                setSignUpState()
            }
            LOGIN -> {
                signInWithGoogl()
            }
            SIGN_UP -> {
                signUpWithGoogl()
            }
        }
    }


    enum class State {
        MAIN,
        LOGIN,
        SIGN_UP,
        LOADER
    }
}
