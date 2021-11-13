package com.android.opus.ui.screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View


import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.Group
import androidx.fragment.app.Fragment
import com.android.opus.R
import com.android.opus.domain.MainScreenInteractor
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import kotlinx.coroutines.Dispatchers

class MainScreenFragment : Fragment(), MainScreenView, View.OnClickListener {

    private val presenter = MainScreenPresenter(
        interactor = MainScreenInteractor(dispatcher = Dispatchers.Default),
        mainDispatcher = Dispatchers.Main.immediate
    )

    private var state: State = State.MAIN

    private var topBarrier: View? = null

    private var appTitle: TextView? = null
    private var greetingTitle: TextView? = null

    private var loginEmailInputForm: TextInputLayout? = null
    private var loginFirstPasswordInputForm: TextInputLayout? = null

    private var nameInputForm: TextInputLayout? = null
    private var surnameInputForm: TextInputLayout? = null
    private var signUpEmailInputForm: TextInputLayout? = null
    private var signUpFirstPasswordInputForm: TextInputLayout? = null
    private var signUpSecondPasswordInputForm: TextInputLayout? = null

    private var signUpBtn: TextView? = null
    private var signGooglBtnText: TextView? = null
    private var signGooglBtn: View? = null
    private var enterBtn: TextView? = null

    private var loginFormGroup: Group? = null
    private var signUpFormGroup: Group? = null

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

        initViews(view)
        setBtnListeners()

        presenter.attachView(this)
    }

    override fun onDestroyView() {
        topBarrier = null
        appTitle = null
        greetingTitle = null
        nameInputForm = null
        surnameInputForm = null
        loginEmailInputForm = null
        signUpEmailInputForm = null
        loginFirstPasswordInputForm = null
        signUpFirstPasswordInputForm = null
        signUpSecondPasswordInputForm = null
        signUpBtn = null
        enterBtn = null
        signGooglBtnText = null
        signGooglBtn = null
        loginFormGroup = null
        signUpFormGroup = null

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
                            loginEmailInputForm,
                            R.id.login_email
                    )
                    val password = getTitleFromTextInputEditTextInTextInputLayout(
                            loginFirstPasswordInputForm,
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
                            nameInputForm,
                            R.id.sign_up_name
                    )
                    val surname = getTitleFromTextInputEditTextInTextInputLayout(
                            surnameInputForm,
                            R.id.sign_up_surname
                    )
                    val email = getTitleFromTextInputEditTextInTextInputLayout(
                            signUpEmailInputForm,
                            R.id.sign_up_email
                    )
                    val firstPassword = getTitleFromTextInputEditTextInTextInputLayout(
                            signUpFirstPasswordInputForm,
                            R.id.sign_up_first_password
                    )
                    val secondPassword = getTitleFromTextInputEditTextInTextInputLayout(
                            signUpSecondPasswordInputForm,
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
        nameInputForm?.error = " "
    }

    override fun showSurnameError() {
        surnameInputForm?.error = " "
    }

    override fun showEmailError() {
        if (state == State.LOGIN) {
            loginEmailInputForm?.error = " "
        } else if (state == State.SIGN_UP) {
            signUpEmailInputForm?.error = " "
        }
    }

    override fun showPasswordError() {
        if (state == State.LOGIN) {
            loginFirstPasswordInputForm?.error = " "
        } else if (state == State.SIGN_UP) {
            signUpFirstPasswordInputForm?.error = " "
        }
    }

    override fun showConfirmError() {
        signUpSecondPasswordInputForm?.errorIconDrawable = resources.getDrawable(R.drawable.ic_error)
        signUpSecondPasswordInputForm?.error = " "
    }

    override fun showConfirmSuccess() {
        signUpSecondPasswordInputForm?.errorIconDrawable = resources.getDrawable(R.drawable.ic_success)
        signUpSecondPasswordInputForm?.error = " "
    }

    override fun clearErrorsInForms() {
        loginEmailInputForm?.isErrorEnabled = false
        loginFirstPasswordInputForm?.isErrorEnabled = false
        nameInputForm?.isErrorEnabled = false
        surnameInputForm?.isErrorEnabled = false
        signUpEmailInputForm?.isErrorEnabled = false
        signUpFirstPasswordInputForm?.isErrorEnabled = false
        signUpSecondPasswordInputForm?.isErrorEnabled = false
    }


    private fun initViews(view: View) {
        topBarrier = view.findViewById(R.id.top_barrier)

        appTitle = view.findViewById(R.id.appTitleForm)
        greetingTitle = view.findViewById(R.id.greeting_title_form)

        loginEmailInputForm = view.findViewById(R.id.login_email_layout)
        loginFirstPasswordInputForm = view.findViewById(R.id.login_first_password_layout)

        nameInputForm = view.findViewById(R.id.sign_up_name_layout)
        surnameInputForm = view.findViewById(R.id.sign_up_surname_layout)
        signUpEmailInputForm = view.findViewById(R.id.sign_up_email_layout)
        signUpFirstPasswordInputForm = view.findViewById(R.id.sign_up_first_password_layout)
        signUpSecondPasswordInputForm = view.findViewById(R.id.sign_up_second_password_layout)

        signUpBtn = view.findViewById(R.id.sign_btn)
        enterBtn = view.findViewById(R.id.enter_btn)
        signGooglBtnText = view.findViewById(R.id.btn_googl_sign)
        signGooglBtn = view.findViewById(R.id.btn_googl_sign_layout)
        loginFormGroup = view.findViewById(R.id.login_group)
        signUpFormGroup = view.findViewById(R.id.sign_up_layout_group)
    }

    private fun setBtnListeners() {
        signUpBtn?.setOnClickListener(this)
        signGooglBtn?.setOnClickListener(this)
        enterBtn?.setOnClickListener(this)
    }

    //упростить
    private fun hideMainStateShowLoginState() {
        greetingTitle?.setText(R.string.login_screen_greeting_title)
        signGooglBtnText?.setText(R.string.sign_in_with_google)

        hideMainStateViews()
        showViewsByStateGroup(loginFormGroup)

        state = State.LOGIN
    }

    private fun hideMainStateShowSignUpState() {
        greetingTitle?.setText(R.string.sign_up_screen_greeting_title)
        signGooglBtnText?.setText(R.string.sign_up_with_google)
        enterBtn?.setText(R.string.continue_text)

        hideMainStateViews()
        showViewsByStateGroup(signUpFormGroup)

        state = State.SIGN_UP
    }

    private fun showMainState() {
        topBarrier?.visibility = View.INVISIBLE

        if (state == State.LOGIN) {
            signUpFormGroup?.visibility = View.GONE
        } else if (state == State.SIGN_UP) {
            loginFormGroup?.visibility = View.GONE
        }

        greetingTitle?.setText(R.string.main_screen_greeting_title)
        signUpBtn?.visibility = View.VISIBLE
        state = State.MAIN
    }

    private fun hideMainStateViews() {
        enterBtn?.visibility = View.GONE
        signUpBtn?.visibility = View.GONE
        topBarrier?.visibility = View.GONE
    }

    private fun showViewsByStateGroup(view: View?) {
        view?.visibility = View.VISIBLE
        enterBtn?.visibility = View.VISIBLE
    }

    private fun getTitleFromTextInputEditTextInTextInputLayout(
            view: TextInputLayout?,
            id: Int): String
    {
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
}
