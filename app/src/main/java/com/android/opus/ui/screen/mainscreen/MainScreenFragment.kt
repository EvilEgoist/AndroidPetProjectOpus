package com.android.opus.ui.screen.mainscreen

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.observe
import com.android.opus.R
import com.android.opus.domain.MainScreenInteractor
import com.android.opus.ui.view.loader.LoadingDialog
import kotlinx.android.synthetic.main.fragment_main_screen.*
import kotlinx.coroutines.Dispatchers

class MainScreenFragment : Fragment(R.layout.fragment_main_screen), View.OnClickListener {

    private var viewModel = MainScreenViewModel(
        MainScreenInteractor(Dispatchers.Default)
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setListeners()
        viewModel.state.observe(this.viewLifecycleOwner, this::updateViewState)
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.enter_btn -> viewModel.enterBtnClicked()
            R.id.sign_btn -> viewModel.signBtnClicked()
        }
    }

    private fun updateViewState(state: MainScreenViewModel.State) {
        if (state == MainScreenViewModel.State.LOGIN) {
            greeting_title_form?.setText(R.string.login_screen_greeting_title)
            btn_googl_sign?.setText(R.string.sign_in_with_google)

            hideMainStateViews()
            showViewsByStateGroup(login_group)
        } else if (state == MainScreenViewModel.State.SIGN_UP) {
            greeting_title_form?.setText(R.string.sign_up_screen_greeting_title)
            btn_googl_sign?.setText(R.string.sign_up_with_google)
            enter_btn?.setText(R.string.continue_text)

            hideMainStateViews()
            showViewsByStateGroup(sign_up_layout_group)
        } else if (state == MainScreenViewModel.State.LOADER) {
            LoadingDialog(requireActivity()).startLoadingDialog()
        }
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

    private fun setListeners() {
        sign_btn?.setOnClickListener(this)
        btn_googl_sign_layout?.setOnClickListener(this)
        enter_btn?.setOnClickListener(this)

        sign_up_first_password.addContentChangeListener { content ->
            sign_up_second_password.contentToConfirm = content
        }
    }

    companion object {
        fun newInstance(): MainScreenFragment = MainScreenFragment()
    }
}
