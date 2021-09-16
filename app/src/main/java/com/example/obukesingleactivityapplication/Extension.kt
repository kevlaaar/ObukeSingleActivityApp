package com.example.obukesingleactivityapplication

import android.content.Context
import android.util.Patterns
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson


fun String.isValidEmail(): Boolean {
    return this.isNotEmpty() && Patterns.EMAIL_ADDRESS.matcher(this).matches()
}

fun View.showInfoSnackBar(message: String?) {
    Snackbar.make(
        this,
        message?:"",
        Snackbar.LENGTH_SHORT
    ).show()
}

fun <T> fromJson(jsonString: String?, typeOfClass: Class<T>?): T {
    return Gson().fromJson(jsonString, typeOfClass)
}

fun <T> toJson(typeOfClass: Class<T>): String {
    return Gson().toJson(typeOfClass)
}

fun View.hideKeyboard() {
    val imm =(context.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager)
    imm?.hideSoftInputFromWindow(windowToken, 0)
}
