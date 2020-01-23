package com.beok.knifeworker.ext

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.datetime.timePicker
import java.util.*

@BindingAdapter("showTimePicker")
fun TextView.showTimePicker(startWorkingHour: (Calendar) -> Unit) {
    setOnClickListener {
        MaterialDialog(it.context).show {
            timePicker { _, datetime -> startWorkingHour.invoke(datetime) }
        }
    }
}