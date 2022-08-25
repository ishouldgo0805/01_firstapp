package ru.netology.nmedia.util

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager

internal fun View.hideKeyboard() {
    val imn = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imn.hideSoftInputFromWindow(windowToken, 0)
}

internal fun View.showKeyboard() {
    val imn = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imn.showSoftInput(this, 0)
}