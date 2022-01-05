package com.vyshas.newsapp.common

import android.content.Context
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import java.util.*

fun EditText.dismissKeyboard() {
    val imm: InputMethodManager? = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
    imm?.hideSoftInputFromWindow(windowToken, 0)
}

/**
 * Returns [Boolean] based on current time.
 * Returns true if hours are between 06:00 pm - 07:00 am
 */
fun isNight(): Boolean {
    val currentHour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
    return (currentHour <= 7 || currentHour >= 18)
}