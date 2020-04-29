package com.beok.knifeworker.util

import android.view.View

class SingleClickListener(
    private val clickListener: View.OnClickListener,
    private val interval: Long = 1000L
) : View.OnClickListener {

    private var clickable = true

    override fun onClick(v: View?) {
        if (clickable) {
            clickable = false
            v?.postDelayed({ clickable = true }, interval)
            clickListener.onClick(v)
        }
    }
}