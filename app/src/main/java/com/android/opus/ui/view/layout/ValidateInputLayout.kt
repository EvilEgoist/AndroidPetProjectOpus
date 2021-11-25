package com.android.opus.ui.view.layout

import android.content.Context
import android.util.AttributeSet
import com.android.opus.ui.view.edit.text.BaseEditTextView

class ValidateInputLayout : BaseValidateInputLayout {

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    override fun init() {
        if (editText != null && editText is BaseEditTextView) {
            baseEditText = editText as BaseEditTextView
            baseEditText!!.addConfirmationStateListener { shouldShowError, _ ->
                if (shouldShowError) {
                    error = " "
                } else {
                    isErrorEnabled = false
                }
                refreshDrawableState()
            }
        }
    }
}
