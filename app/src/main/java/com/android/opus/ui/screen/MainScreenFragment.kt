package com.android.opus.ui.screen

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View

import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.android.opus.R
import com.android.opus.domain.MainScreenInteractor
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import kotlinx.android.synthetic.main.fragment_main_screen.*
import kotlinx.coroutines.Dispatchers

class MainScreenFragment : Fragment(), MainScreenView, View.OnClickListener,
    View.OnFocusChangeListener {

    private val presenter = MainScreenPresenter(
        interactor = MainScreenInteractor(dispatcher = Dispatchers.Default),
        mainDispatcher = Dispatchers.Main.immediate
    )

    private var state: State = State.MAIN

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main_screen, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        state = State.MAIN

        setListeners()
        presenter.attachView(this)
    }

    override fun onDestroyView() {
        presenter.detachView()
        super.onDestroyView()
    }

    override fun onDestroy() {
        presenter.onDestroy()
        super.onDestroy()
    }

    override fun onClick(v: View?) {
        if (state == State.MAIN) {
            when (v!!.id) {
                R.id.sign_btn -> {
                    hideMainStateShowSignUpState()
                }
                R.id.enter_btn -> {
                    hideMainStateShowLoginState()
                }
            }
        } else if (state == State.LOGIN) {
            when (v!!.id) {
                R.id.sign_btn -> presenter.signInWithGoogl()
                R.id.enter_btn -> {

                    val email = getTitleFromTextInputEditTextInTextInputLayout(
                        login_email_layout,
                        R.id.login_email
                    )
                    val password = getTitleFromTextInputEditTextInTextInputLayout(
                        login_first_password_layout,
                        R.id.login_first_password
                    )

                    presenter.login(email = email, password = password)
                }
            }
        } else if (state == State.SIGN_UP) {
            when (v!!.id) {
                R.id.sign_btn -> presenter.signUpWithGoogl()
                R.id.enter_btn -> {

                    val name = getTitleFromTextInputEditTextInTextInputLayout(
                        sign_up_name_layout,
                        R.id.sign_up_name
                    )
                    val surname = getTitleFromTextInputEditTextInTextInputLayout(
                        sign_up_surname_layout,
                        R.id.sign_up_surname
                    )
                    val email = getTitleFromTextInputEditTextInTextInputLayout(
                        sign_up_email_layout,
                        R.id.sign_up_email
                    )
                    val firstPassword = getTitleFromTextInputEditTextInTextInputLayout(
                        sign_up_first_password_layout,
                        R.id.sign_up_first_password
                    )
                    val secondPassword = getTitleFromTextInputEditTextInTextInputLayout(
                        sign_up_second_password_layout,
                        R.id.sign_up_second_password
                    )

                    presenter.signUp(
                        name = name,
                        surname = surname,
                        email = email,
                        firstPassword = firstPassword,
                        secondPassword = secondPassword
                    )
                }
            }
        }
    }

    override fun showNameError() {
        sign_up_name_layout?.error = " "
    }

    override fun showSurnameError() {
        sign_up_surname_layout?.error = " "
    }

    override fun showEmailError() {
        if (state == State.LOGIN) {
            login_email_layout?.error = " "
        } else if (state == State.SIGN_UP) {
            sign_up_email_layout?.error = " "
        }
    }

    override fun showPasswordError() {
        if (state == State.LOGIN) {
            login_first_password_layout?.error = " "
        } else if (state == State.SIGN_UP) {
            sign_up_first_password_layout?.error = " "
        }
    }

    override fun showConfirmError() {
        sign_up_second_password_layout?.errorIconDrawable =
            resources.getDrawable(R.drawable.ic_error)
        sign_up_second_password_layout?.error = " "
    }

    override fun showConfirmSuccess() {
        sign_up_second_password_layout?.errorIconDrawable =
            resources.getDrawable(R.drawable.ic_success)
        sign_up_second_password_layout?.error = " "
    }

    override fun clearErrorsInForms() {
        login_email_layout?.isErrorEnabled = false
        login_first_password_layout?.isErrorEnabled = false
        sign_up_name_layout?.isErrorEnabled = false
        sign_up_surname_layout?.isErrorEnabled = false
        sign_up_email_layout?.isErrorEnabled = false
        sign_up_first_password_layout?.isErrorEnabled = false
        sign_up_second_password_layout?.isErrorEnabled = false
    }

    private fun setListeners() {
        sign_btn?.setOnClickListener(this)
        btn_googl_sign_layout?.setOnClickListener(this)
        enter_btn?.setOnClickListener(this)
        sign_up_second_password?.onFocusChangeListener = this
    }

    private fun hideMainStateShowLoginState() {
        greeting_title_form?.setText(R.string.login_screen_greeting_title)
        btn_googl_sign?.setText(R.string.sign_in_with_google)

        hideMainStateViews()
        showViewsByStateGroup(login_group)

        state = State.LOGIN
    }

    private fun hideMainStateShowSignUpState() {
        greeting_title_form?.setText(R.string.sign_up_screen_greeting_title)
        btn_googl_sign?.setText(R.string.sign_up_with_google)
        enter_btn?.setText(R.string.continue_text)

        hideMainStateViews()
        showViewsByStateGroup(sign_up_layout_group)

        state = State.SIGN_UP
    }

    private fun hideMainStateViews() {
        enter_btn?.visibility = View.GONE
        sign_btn?.visibility = View.GONE
        top_barrier?.visibility = View.GONE
    }

    private fun showViewsByStateGroup(view: View?) {
        view?.visibility = View.VISIBLE
        enter_btn?.visibility = View.VISIBLE
    }

    private fun getTitleFromTextInputEditTextInTextInputLayout(
        view: TextInputLayout?,
        id: Int
    ): String {
        val viewWithTitle: TextInputEditText? = view?.findViewById(id)
        return viewWithTitle?.text.toString()
    }

    private enum class State {
        MAIN,
        LOGIN,
        SIGN_UP
    }

    companion object {
        fun newInstance() = MainScreenFragment()
    }

    override fun onFocusChange(v: View?, hasFocus: Boolean) {
        if (!hasFocus) {
            when (v!!.id) {

                R.id.sign_up_second_password -> {
                    val firstPassword = getTitleFromTextInputEditTextInTextInputLayout(
                        sign_up_first_password_layout,
                        R.id.sign_up_first_password
                    )
                    val secondPassword = getTitleFromTextInputEditTextInTextInputLayout(
                        sign_up_second_password_layout,
                        R.id.sign_up_second_password
                    )
                    presenter.passwordConfirmCheck(
                        firstPassword,
                        secondPassword
                    )
                }
            }
        }
    }
}
