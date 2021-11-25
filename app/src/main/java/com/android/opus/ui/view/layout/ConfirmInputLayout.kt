package com.android.opus.ui.view.layout

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import com.android.opus.R
import com.android.opus.ui.view.edit.text.BaseEditTextView

class ConfirmInputLayout : BaseValidateInputLayout {

    private var iconErrorTint: Int = -1
    private var iconConfirmTint: Int = -1
    private var iconErrorDrawable: Drawable? = null
    private var iconConfirmDrawable: Drawable? = null

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        initAttrs(context, attrs, 0)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        initAttrs(context, attrs, defStyleAttr)
    }

    private fun initAttrs(context: Context, attrs: AttributeSet?, defStyleAttr: Int) {
        var a = context.obtainStyledAttributes(attrs, R.styleable.TextInputLayout)
        iconErrorDrawable = a.getDrawable(R.styleable.TextInputLayout_errorIconDrawable)
        iconErrorTint = a.getColor(R.styleable.TextInputLayout_errorIconTint, -1)
        a.recycle()
        a = context.obtainStyledAttributes(attrs, R.styleable.InputLayoutConfirmationState)
        iconConfirmDrawable =
            a.getDrawable(R.styleable.InputLayoutConfirmationState_confirmIconDrawable)
        iconConfirmTint = a.getColor(R.styleable.InputLayoutConfirmationState_confirmIconTint, -1)
        iconErrorTint
        a.recycle()
    }

    override fun init() {
        if (editText != null && editText is BaseEditTextView) {
            baseEditText = editText as BaseEditTextView
            baseEditText!!.addConfirmationStateListener { shouldShowError, shouldShowConfirmed ->
                if (shouldShowError && !shouldShowConfirmed) {
                    errorIconDrawable = iconErrorDrawable
                    errorIconDrawable?.setTint(iconErrorTint)
                    error = " "
                } else if (!shouldShowError && shouldShowConfirmed) {
                    errorIconDrawable = iconConfirmDrawable
                    errorIconDrawable?.setTint(iconConfirmTint)
                    error = " "
                } else if (!shouldShowError && !shouldShowConfirmed) {
                    isErrorEnabled = false
                }
                refreshDrawableState()
            }
        }
    }
}
