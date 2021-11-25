package com.android.opus.ui.view.layout

import android.content.Context
import android.util.AttributeSet
import com.android.opus.ui.view.edit.text.BaseEditTextView
import com.google.android.material.textfield.TextInputLayout

abstract class BaseValidateInputLayout : TextInputLayout {

    protected var baseEditText: BaseEditTextView? = null

    constructor(context: Context) : super(context) {
        viewTreeObserver.addOnGlobalLayoutListener {
            init()
        }
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        viewTreeObserver.addOnGlobalLayoutListener {
            init()
        }
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        viewTreeObserver.addOnGlobalLayoutListener {
            init()
        }
    }

    protected abstract fun init()
}