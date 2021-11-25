package com.android.opus.ui.view.edit.text

import android.content.Context
import android.os.CountDownTimer
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.View
import android.view.View.OnFocusChangeListener
import com.android.opus.R
import com.google.android.material.textfield.TextInputEditText

typealias ContentChangeWatcher = (content: String) -> Unit
typealias EditTextConfirmationWatcher
        = (shouldShowError: Boolean, shouldShowConfirmed: Boolean) -> Unit

abstract class BaseEditTextView : TextInputEditText {

    private var hasFocus = false
    private var hasBeenEdited = false
    private var shouldShowErrorIcon = false
    private var shouldShowConfirmIcon = false

    var contentToConfirm: String? = null

    private val stateError = intArrayOf(R.attr.state_error)
    private val stateConfirmed = intArrayOf(R.attr.state_confirmed)

    private var errorShowStateListeners: ArrayList<EditTextConfirmationWatcher>? = null
    private var contentChangeListeners: ArrayList<ContentChangeWatcher>? = null

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init()
    }

    protected abstract fun isValid(): Boolean

    protected abstract fun isValidContentToConfirm(): Boolean

    protected abstract fun processText()

    fun addContentChangeListener(watcher: ContentChangeWatcher) {
        if (contentChangeListeners == null) {
            contentChangeListeners = ArrayList()
        }
        contentChangeListeners?.add(watcher)
    }

    private fun sendChangeContent(password: String) {
        contentChangeListeners?.forEach { it ->
            it.invoke(password)
        }
    }

    fun removeContentChangeListener(watcher: ContentChangeWatcher) {
        if (contentChangeListeners != null) {
            contentChangeListeners?.remove(watcher)
        }
    }

    fun addConfirmationStateListener(watcher: EditTextConfirmationWatcher) {
        if (errorShowStateListeners == null) {
            errorShowStateListeners = ArrayList()
        }
        errorShowStateListeners?.add(watcher)
    }

    private fun sendConfirmationState(shouldShowError: Boolean, shouldShowConfirm: Boolean) {
        errorShowStateListeners?.forEach { it ->
            it.invoke(shouldShowError, shouldShowConfirm)
        }
    }

    fun removeConfirmationStateListener(watcher: EditTextConfirmationWatcher) {
        if (errorShowStateListeners != null) {
            errorShowStateListeners?.remove(watcher)
        }
    }

    override fun onCreateDrawableState(extraSpace: Int): IntArray {
        val drawableState = super.onCreateDrawableState(extraSpace + 4)
        if (shouldShowErrorIcon && !shouldShowConfirmIcon) {
            View.mergeDrawableStates(drawableState, stateError)
        } else if (shouldShowConfirmIcon && !shouldShowErrorIcon) {
            View.mergeDrawableStates(drawableState, stateConfirmed)
        }
        return drawableState
    }

    private fun init() {
        addTextChangedListener(object : TextWatcher {
            var timer: CountDownTimer? = null
            override fun afterTextChanged(s: Editable?) {
                updateStateByNotShowIcon()
                timer?.cancel()
                timer = object : CountDownTimer(FUTURE_TIME, COUNT_DOWN_INTERVAL) {
                    override fun onTick(millisUntilFinished: Long) {}
                    override fun onFinish() {
                        processText()
                        sendChangeContent(text.toString())
                        forceUpdateState()
                    }
                }.start()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(text: CharSequence, start: Int, before: Int, count: Int) {
                hasBeenEdited = true
            }
        })

        onFocusChangeListener = OnFocusChangeListener { _, hasFocus ->
            this.hasFocus = hasFocus
            updateState()
            sendChangeContent(text.toString())
        }
    }

    fun updateStateByNotShowIcon() {
        shouldShowErrorIcon = false
        shouldShowConfirmIcon = false
        sendConfirmationState(shouldShowErrorIcon, shouldShowConfirmIcon)
        refreshDrawableState()
    }

    fun forceUpdateState() {
        shouldShowErrorIcon = !isValid() && isValidContentToConfirm() && text!!.isNotEmpty()
        shouldShowConfirmIcon = isValid() && isValidContentToConfirm()
        sendConfirmationState(shouldShowErrorIcon, shouldShowConfirmIcon)
        refreshDrawableState()
    }

    private fun updateState() {
        shouldShowErrorIcon = !hasFocus && hasBeenEdited && !isValid()
                && text!!.isNotEmpty() && isValidContentToConfirm()
        shouldShowConfirmIcon = !hasFocus && hasBeenEdited && isValid() && isValidContentToConfirm()
        sendConfirmationState(shouldShowErrorIcon, shouldShowConfirmIcon)
        refreshDrawableState()
    }

    companion object {
        const val FUTURE_TIME: Long = 2000
        const val COUNT_DOWN_INTERVAL: Long = 2000
    }
}
