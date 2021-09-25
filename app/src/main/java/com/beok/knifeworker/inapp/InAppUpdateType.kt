package com.beok.knifeworker.inapp

import com.google.android.play.core.appupdate.AppUpdateInfo

sealed class InAppUpdateType {

    object Impossible : InAppUpdateType()

    data class Possible(val type: Int, val info: AppUpdateInfo) : InAppUpdateType()
}

