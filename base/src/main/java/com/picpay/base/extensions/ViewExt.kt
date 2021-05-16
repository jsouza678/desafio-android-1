package com.picpay.base.extensions

import android.view.View
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar

fun View.showErrorSnackbar(message: String) {
    Snackbar.make(
        this,
        message,
        BaseTransientBottomBar.LENGTH_LONG
    ).setBackgroundTint(
        androidx.core.content.ContextCompat.getColor(
            this.context,
            com.picpay.sharedcomponents.R.color.colorError
        )).show()
}