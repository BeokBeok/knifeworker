package com.beok.knifeworker.ext

import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.datetime.timePicker
import com.google.android.material.textfield.TextInputEditText
import java.util.*

interface BindingAdapterListener {
    fun invoke()
}

@BindingAdapter("showTimePicker")
fun TextView.showTimePicker(startWorkingHour: (Calendar) -> Unit) {
    setOnSingleClickListener {
        MaterialDialog(it.context).show {
            timePicker { _, datetime -> startWorkingHour.invoke(datetime) }
        }
    }
}

@BindingAdapter("doneKeyPad")
fun TextInputEditText.doneKeyPad(listener: BindingAdapterListener) {
    setOnEditorActionListener { _, actionId, _ ->
        if (actionId == EditorInfo.IME_ACTION_DONE) {
            listener.invoke()
            false
        } else {
            true
        }
    }
}