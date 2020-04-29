package com.beok.knifeworker.ext

import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.datetime.timePicker
import com.beok.knifeworker.MainViewModel
import com.google.android.material.textfield.TextInputEditText
import java.util.*

@BindingAdapter("showTimePicker")
fun TextView.showTimePicker(startWorkingHour: (Calendar) -> Unit) {
    setOnSingleClickListener {
        MaterialDialog(it.context).show {
            timePicker { _, datetime -> startWorkingHour.invoke(datetime) }
        }
    }
}

@BindingAdapter("showWorkOffTime")
fun TextInputEditText.showWorkOffTime(viewModel: MainViewModel) {
    setOnEditorActionListener { textView, actionId, _ ->
        if (actionId == EditorInfo.IME_ACTION_DONE) {
            viewModel.showWorkOffTime(textView.text.toString())
            false
        } else {
            true
        }
    }
}