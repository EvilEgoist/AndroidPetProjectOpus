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
import com.google.android.material.textfield.TextInputLayout
import kotlinx.coroutines.Dispatchers


class MainScreenFragment : Fragment(), MainScreenView, View.OnClickListener {
    private val presenter = MainScreenPresenter(
        interactor = MainScreenInteractor(dispatcher = Dispatchers.Default),
        mainDispatcher = Dispatchers.Main.immediate
    )

    private var state: State = State.MAIN

    private var topBarrier: View? = null

    private var appTitle: View? = null
    private var greetingTitle: View? = null

    private var loginEmailInputForm: TextInputLayout? = null
    private var loginFirstPasswordInputForm: TextInputLayout? = null

    private var nameInputForm: TextInputLayout? = null
    private var surnameInputForm: TextInputLayout? = null
    private var signUpEmailInputForm: TextInputLayout? = null
    private var signUpFirstPasswordInputForm: TextInputLayout? = null
    private var signUpSecondPasswordInputForm: TextInputLayout? = null

    private var signUpBtn: View? = null
    private var signGooglBtn: View? = null
    private var enterBtn: View? = null

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
        setBeginingContent()
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
                    initSignUpStateContent()
                    showSignUpState()
                }
                R.id.enter_btn -> {
                    initLoginStateContent()
                    showLoginState()
                }
            }
        } else if (state == State.LOGIN) {
            when (v!!.id) {
                R.id.sign_btn -> presenter.signInWithGoogl()
                R.id.enter_btn -> {
                    var content: TextView? = loginEmailInputForm?.findViewById(R.id.email_form)
                    var email: String = content?.text.toString()
                    content = loginFirstPasswordInputForm?.findViewById(R.id.first_password)
                    var password: String = content?.text.toString()

                    presenter.login(email = email, password = password)
                }
            }
        } else if (state == State.SIGN_UP) {
            when (v!!.id) {
                R.id.sign_btn -> presenter.signUpWithGoogl()
                R.id.enter_btn -> {
                    var content: TextView? = nameInputForm?.findViewById(R.id.username_form)
                    var name: String = content?.text.toString()
                    content = surnameInputForm?.findViewById(R.id.username_form)
                    var surname: String = content?.text.toString()
                    content = signUpEmailInputForm?.findViewById(R.id.email_form)
                    var email: String = content?.text.toString()
                    content = signUpFirstPasswordInputForm?.findViewById(R.id.first_password)
                    var firstPassword: String = content?.text.toString()
                    content = signUpSecondPasswordInputForm?.findViewById(R.id.second_password)
                    var secondPassword: String = content?.text.toString()

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
        signUpSecondPasswordInputForm?.errorIconDrawable= resources.getDrawable(R.drawable.ic_error)
        signUpSecondPasswordInputForm?.error = " "
    }

    override fun showConfirmSuccess() {
        signUpSecondPasswordInputForm?.errorIconDrawable= resources.getDrawable(R.drawable.ic_success)
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
        appTitle = view.findViewById(R.id.app_title_form)
        greetingTitle = view.findViewById(R.id.greeting_title_form)
        nameInputForm = view.findViewById(R.id.sign_up_name_form)
        surnameInputForm = view.findViewById(R.id.sign_up_surname_form)
        loginEmailInputForm = view.findViewById(R.id.login_email_form)
        signUpEmailInputForm = view.findViewById(R.id.sign_up_email_form)
        loginFirstPasswordInputForm = view.findViewById(R.id.login_frist_password_form)
        signUpFirstPasswordInputForm = view.findViewById(R.id.sign_up_first_password_form)
        signUpSecondPasswordInputForm = view.findViewById(R.id.sign_up_second_password_form)
        signUpBtn = view.findViewById(R.id.sign_btn)
        enterBtn = view.findViewById(R.id.enter_btn)
        signGooglBtn = view.findViewById(R.id.sign_googl_btn)
        loginFormGroup = view.findViewById(R.id.login_form_group)
        signUpFormGroup = view.findViewById(R.id.sign_up_form_group)
    }

    private fun setBeginingContent() {
        setAppTitle(R.string.app_name)
        setGreetingTitle(R.string.main_screen_greeting_title)
        setTitleBackgroundSignUpBtn(R.id.btn_base, R.string.sign_up, R.drawable.shape_btn_sign_up, signUpBtn)
        setTitleBackgroundEnterBtn(R.id.btn_base, R.string.enter, R.drawable.shape_btn_enter, enterBtn)
    }

    private fun setBtnListeners() {
        signUpBtn?.setOnClickListener(this)
        signGooglBtn?.setOnClickListener(this)
        enterBtn?.setOnClickListener(this)
    }


    private fun showLoginState() {
        setGreetingTitle(R.string.login_screen_greeting_title)
        hideMainStateViews()
        showViewsByStateGroup(loginFormGroup)
        state = State.LOGIN
    }

    private fun showSignUpState() {
        setGreetingTitle(R.string.sign_up_screen_greeting_title)
        hideMainStateViews()
        showViewsByStateGroup(signUpFormGroup)
        state = State.SIGN_UP
    }

    private fun showMainState() {
        topBarrier?.visibility = View.INVISIBLE
        if (state == State.LOGIN) {
            hideLoginStateViews()
        } else if (state == State.SIGN_UP) {
            hideSignUpStateViews()
        }
        setGreetingTitle(R.string.main_screen_greeting_title)
        signUpBtn?.visibility = View.VISIBLE
        state = State.MAIN
    }

    private fun hideMainStateViews() {
        enterBtn?.visibility = View.GONE
        signUpBtn?.visibility = View.GONE
        topBarrier?.visibility = View.GONE
    }

    private fun hideSignUpStateViews() {
        signUpFormGroup?.visibility = View.GONE
        signGooglBtn?.visibility = View.GONE
    }

    private fun hideLoginStateViews() {
        loginFormGroup?.visibility = View.GONE
        signGooglBtn?.visibility = View.GONE
    }

    private fun showViewsByStateGroup(view: View?) {
        view?.visibility = View.VISIBLE
        signGooglBtn?.visibility = View.VISIBLE
        enterBtn?.visibility = View.VISIBLE
    }

    private fun initSignUpStateContent() {
        setHintBackgroundInputForm(
            R.id.username_form,
            R.string.hint_user_name,
            R.drawable.shape_edit_text_top_rounded,
            nameInputForm
        )

        setHintBackgroundInputForm(
            R.id.username_form,
            R.string.hint_user_surname,
            R.drawable.shape_edit_text_rectangle,
            surnameInputForm
        )

        setHintBackgroundInputForm(
            R.id.email_form,
            R.string.hint_email_title,
            R.drawable.shape_edit_text_rectangle,
            signUpEmailInputForm
        )

        setHintBackgroundInputForm(
            R.id.first_password,
            R.string.hint_first_password_form,
            R.drawable.shape_edit_text_rectangle,
            signUpFirstPasswordInputForm
        )

        setHintBackgroundInputForm(
            R.id.second_password,
            R.string.hint_second_password_from,
            R.drawable.shape_edit_text_bottom_rounded,
            signUpSecondPasswordInputForm
        )

        setTitleGooglBtn(R.id.btn_googl_sign, R.string.sign_up_with_google, signGooglBtn)
        setTitleBackgroundEnterBtn(R.id.btn_base, R.string.sign_up_enter, R.drawable.shape_btn_enter, enterBtn)
    }

    private fun initLoginStateContent() {
        setHintBackgroundInputForm(
            R.id.email_form,
            R.string.hint_email_title,
            R.drawable.shape_edit_text_top_rounded,
            loginEmailInputForm
        )

        setHintBackgroundInputForm(
            R.id.first_password,
            R.string.hint_first_password_form,
            R.drawable.shape_edit_text_bottom_rounded,
            loginFirstPasswordInputForm
        )
        setTitleGooglBtn(R.id.btn_googl_sign, R.string.sign_in_with_google, signGooglBtn)
    }

    private fun setHintBackgroundInputForm(id: Int, hint: Int, background: Int, view: View?) {
        val temp: TextView? = view?.findViewById(id)
        temp?.hint = resources.getString(hint)
        temp?.background = resources.getDrawable(background)
    }

    private fun setTitleGooglBtn(id: Int, text: Int, view: View?) {
        val temp: TextView? = view?.findViewById(id)
        temp?.text = resources.getString(text)
    }

    private fun setTitleBackgroundSignUpBtn(id: Int, text: Int, background: Int, view: View?) {
        val temp: TextView? = view?.findViewById(id)
        temp?.background = resources.getDrawable(background)
        temp?.text = resources.getString(text)
    }

    private fun setTitleBackgroundEnterBtn(id: Int, text: Int, background: Int, view: View?) {
        val temp: TextView? = view?.findViewById(id)
        temp?.background = resources.getDrawable(background)
        temp?.text = resources.getString(text)
    }

    private fun setGreetingTitle(text: Int) {
        var temp: TextView? = view?.findViewById(R.id.greeting_title)
        temp?.text = resources.getString(text)
    }

    private fun setAppTitle(text: Int) {
        var temp: TextView? = appTitle?.findViewById(R.id.app_title)
        temp?.text = resources.getString(text)
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