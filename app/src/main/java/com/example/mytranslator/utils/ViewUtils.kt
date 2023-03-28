package com.example.mytranslator.utils

import android.view.View
import com.google.android.material.snackbar.Snackbar

fun View.show() {
    visibility = View.VISIBLE
}

fun View.hide() {
    visibility = View.GONE
}

fun makeSnackbar(
    view: View,
    text: String = String.EMPTY,
    actionText: String = String.EMPTY,
    action: (View) -> Unit = {},
    length: Int = Snackbar.LENGTH_LONG,
    anchor: View? = null
) {
    Snackbar
        .make(view, text, length)
        .setAction(actionText, action)
        .setAnchorView(anchor)
        .show()
}