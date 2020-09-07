package com.beok.knifeworker.util

import androidx.preference.PreferenceManager
import com.beok.knifeworker.MyApplication

object Pref {

    private const val IS_IN_APP_REVIEW = "IS_IN_APP_REVIEW"

    private val prefs by lazy {
        PreferenceManager.getDefaultSharedPreferences(MyApplication.instance)
    }

    var isInAppReview
        get() = prefs.getBoolean(IS_IN_APP_REVIEW, false)
        set(value) = prefs.edit().putBoolean(IS_IN_APP_REVIEW, value).apply()
}
