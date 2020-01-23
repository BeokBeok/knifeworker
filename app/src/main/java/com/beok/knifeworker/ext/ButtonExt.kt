package com.beok.knifeworker.ext

import android.widget.Button
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.datetime.timePicker
import com.beok.knifeworker.R
import java.util.*

@BindingAdapter("showTimePicker")
fun Button.showTimePicker(targetView: TextView) {
    setOnClickListener {
        val context = it.context
        MaterialDialog(context).show {
            timePicker { _, datetime ->
                val amOrPm = if (datetime.get(Calendar.AM_PM) == 1) {
                    context.getString(R.string.pm)
                } else {
                    context.getString(R.string.am)
                }
                targetView.text =
                    String.format(
                        context.getString(R.string.contents_start_working),
                        amOrPm,
                        datetime.get(Calendar.HOUR),
                        datetime.get(Calendar.MINUTE)
                    )
            }
        }
    }
}