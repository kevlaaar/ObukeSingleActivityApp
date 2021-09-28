package com.example.obukesingleactivityapplication

import android.app.DatePickerDialog
import android.content.Context
import android.util.Patterns
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import java.text.SimpleDateFormat
import java.util.*


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

fun EditText.transformIntoDatePicker(context: Context, format: String, maxDate: Date? = null) {
    isFocusableInTouchMode = false
    isClickable = true
    isFocusable = false

    val myCalendar = Calendar.getInstance()
    val datePickerOnDataSetListener =
        DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
            myCalendar.set(Calendar.YEAR, year)
            myCalendar.set(Calendar.MONTH, monthOfYear)
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            val sdf = SimpleDateFormat(format, Locale.getDefault())
            setText(sdf.format(myCalendar.time))
        }

    setOnClickListener {
        DatePickerDialog(
            context,R.style.DatePickerTheme, datePickerOnDataSetListener, myCalendar
                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
            myCalendar.get(Calendar.DAY_OF_MONTH)
        ).run {
            maxDate?.time?.also { datePicker.maxDate = it }
            show()
        }
    }
}

