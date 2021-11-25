package com.android.opus.ui.view.edit.text

import android.content.Context
import android.util.AttributeSet
import com.android.opus.utils.CheckCorrectUserData


class ConfirmPasswordEditText : BaseEditTextView {

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    override fun processText() {}

    override fun isValidContentToConfirm(): Boolean =
        CheckCorrectUserData.isValidPassword(contentToConfirm.toString())

    override fun isValid(): Boolean =
        CheckCorrectUserData.isConfirmedPassword(contentToConfirm.toString(), text.toString())

}
