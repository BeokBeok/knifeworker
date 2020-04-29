package com.beok.knifeworker.ext

import android.view.View
import com.beok.knifeworker.util.SingleClickListener

fun View.setOnSingleClickListener(action: (v: View) -> Unit) {
    val listener = View.OnClickListener { action.invoke(it) }
    setOnClickListener(SingleClickListener(listener))
}

fun View.setOnSingleClickListener(action: (v: View) -> Unit, interval: Long) {
    val listener = View.OnClickListener { action.invoke(it) }
    setOnClickListener(SingleClickListener(listener, interval))
}