package com.picpay.base.extensions

import android.view.View
import android.widget.ImageView
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import com.picpay.sharedcomponents.R
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso

fun View.showErrorSnackbar(message: String) {
    Snackbar.make(
        this,
        message,
        BaseTransientBottomBar.LENGTH_LONG
    ).setBackgroundTint(
        ContextCompat.getColor(
            this.context,
            R.color.pomegranate_red
        )).show()
}

fun ImageView.loadImage(
    imageUrl: String,
    onSuccess: () -> Unit,
    onError: () -> Unit
) {
    Picasso.get()
        .load(imageUrl)
        .error(R.drawable.ic_round_account_circle)
        .into(this, object : Callback {
            override fun onSuccess() { onSuccess.invoke() }

            override fun onError(e: Exception?) { onError.invoke() }
        })
}

fun View.makeGone() {
    this.visibility = View.GONE
}

fun View.makeVisible() {
    this.visibility = View.VISIBLE
}
