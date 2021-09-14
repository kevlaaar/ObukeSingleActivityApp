package com.example.obukesingleactivityapplication

import android.util.Patterns
import android.view.View
import com.google.android.material.snackbar.Snackbar


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
