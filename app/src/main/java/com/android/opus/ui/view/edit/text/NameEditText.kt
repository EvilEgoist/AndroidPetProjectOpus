package com.android.opus.ui.view.edit.text

import android.content.Context
import android.util.AttributeSet
import com.android.opus.utils.CheckCorrectUserData

class NameEditText : BaseEditTextView {

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    override fun processText() {
        if (text.toString().isNotEmpty() && !text.toString()[0].isUpperCase()) {
            setText(text.toString().capitalize())
            setSelection(text!!.length)
        }
    }

    override fun isValid(): Boolean = CheckCorrectUserData.isValidUserName(text.toString())

    override fun isValidContentToConfirm(): Boolean = true
}
