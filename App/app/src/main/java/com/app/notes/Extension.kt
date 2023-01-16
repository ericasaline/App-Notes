package com.app.notes

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

fun AppCompatActivity.hideSoftKeyboard() {
    if(currentFocus != null) {
        val inputMethodManager: InputMethodManager = getSystemService(
            AppCompatActivity.INPUT_METHOD_SERVICE
        ) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(
            currentFocus!!.windowToken, InputMethodManager.HIDE_NOT_ALWAYS
        )
    }
}

fun AppCompatActivity.showToast(text: String, context: Context) {
    val layout: View = layoutInflater.inflate(
        R.layout.toast_info, findViewById(R.id.toast_layout)
    )
    Toast(context).apply {
        duration = Toast.LENGTH_SHORT
        layout.findViewById<TextView>(R.id.toast_text).text = text
        view = layout
    }.show()
}