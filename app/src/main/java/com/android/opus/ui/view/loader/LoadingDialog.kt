package com.android.opus.ui.view.loader

import android.app.Activity
import android.app.AlertDialog
import com.android.opus.R

class LoadingDialog(
    private val activity: Activity
) {
    private var dialog:  AlertDialog? = null

    fun startLoadingDialog() {
        val builder = AlertDialog.Builder(activity)

        val inflater = activity.layoutInflater
        builder.setView(inflater.inflate(R.layout.fragment_loader, null))
        builder.setCancelable(true)

        dialog = builder.create()
        dialog?.show()
    }

    fun dismissDialog() {
        dialog?.dismiss()
    }
}