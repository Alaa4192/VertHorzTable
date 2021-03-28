package com.akh.verthorztablelibrary

import android.view.View
import com.google.android.material.textfield.TextInputEditText

fun View.showOrGone(show: Boolean) {
    visibility = if (show) {
        View.VISIBLE
    } else {
        View.GONE
    }
}

fun View.show() {
    visibility = View.VISIBLE
}

fun View.gone() {
    visibility = View.GONE
}


fun TextInputEditText.text(): String =
    this.text.toString().trim()

fun TextInputEditText.textOrNull(): String? {
    return if (this.text().isEmpty())
        null
    else
        this.text()
}

