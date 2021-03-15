package com.android.opus.ui.screen

import com.android.opus.domain.MainScreenInteractor
import com.android.opus.domain.RequestResult
import kotlinx.coroutines.*

class MainScreenPresenter(
    private val interactor: MainScreenInteractor,
    private val mainDispatcher: CoroutineDispatcher
) {

    private val presenterScope: CoroutineScope =
        CoroutineScope(SupervisorJob() + mainDispatcher)

    private var view: MainScreenView? = null

    fun attachView(view: MainScreenView) {
        this.view = view
    }

    fun detachView() {
        this.view = null
    }

    fun onDestroy() {
        presenterScope.cancel()
    }

    fun login(email: String, password: String) {
        presenterScope.launch {
            val loginResult = interactor.login(email = email, password = password)

            view?.clearErrorsInForms()

            when (loginResult) {
                is RequestResult.Error.Email -> view?.showEmailError()
                is RequestResult.Error.Password -> view?.showPasswordError()
            }
        }
    }

    fun signUp(
        name: String,
        surname: String,
        email: String,
        firstPassword: String,
        secondPassword: String
    ) {
        presenterScope.launch {
            val signUpResult = interactor.signUp(
                name = name,
                surname = surname,
                email = email,
                firstPassword = firstPassword,
                secondPassword = secondPassword
            )

            view?.clearErrorsInForms()

            when (signUpResult) {
                is RequestResult.Error.Name -> view?.showNameError()
                is RequestResult.Error.Surname -> view?.showSurnameError()
                is RequestResult.Error.Email -> view?.showEmailError()
                is RequestResult.Error.Password -> view?.showPasswordError()
                is RequestResult.Error.ConfirmedPassword -> view?.showConfirmError()
                is RequestResult.Success.ConfirmedPassword -> view?.showConfirmSuccess()
            }

        }
    }

    fun signInWithGoogl() {}
    fun signUpWithGoogl() {}
}
