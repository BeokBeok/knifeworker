package com.beok.knifeworker.inapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.beok.knifeworker.Event
import com.beok.knifeworker.MainActivity
import com.google.android.play.core.appupdate.AppUpdateInfo
import com.google.android.play.core.appupdate.AppUpdateManager
import com.google.android.play.core.install.InstallStateUpdatedListener
import com.google.android.play.core.install.model.AppUpdateType
import com.google.android.play.core.install.model.UpdateAvailability

class InAppUpdateManager(private val inAppUpdateManager: AppUpdateManager) {

    private val _appUpdatable = MutableLiveData<Event<InAppUpdateType>>()
    val appUpdatable: LiveData<Event<InAppUpdateType>> get() = _appUpdatable

    private val _installAndRestart = MutableLiveData<Boolean>()
    val installAndRestart: LiveData<Boolean> get() = _installAndRestart

    fun checkAppUpdatable() {
        inAppUpdateManager.appUpdateInfo
            .addOnSuccessListener { appUpdateInfo ->
                when {
                    appUpdateInfo.updateAvailability() != UpdateAvailability.UPDATE_AVAILABLE ->
                        _appUpdatable.value = Event(InAppUpdateType.Impossible)
                    appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.FLEXIBLE) -> {
                        _appUpdatable.value = Event(
                            InAppUpdateType.Possible(
                                type = AppUpdateType.FLEXIBLE,
                                info = appUpdateInfo
                            )
                        )
                    }
                    appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.IMMEDIATE) -> {
                        _appUpdatable.value = Event(
                            InAppUpdateType.Possible(
                                type = AppUpdateType.IMMEDIATE,
                                info = appUpdateInfo
                            )
                        )
                    }
                }
            }
    }

    fun registerUpdateFlowForResult(
        appUpdateInfo: AppUpdateInfo,
        appUpdateType: Int,
        target: MainActivity
    ) {
        inAppUpdateManager.startUpdateFlowForResult(
            appUpdateInfo,
            appUpdateType,
            target,
            REQ_IN_APP_UPDATE
        )
    }

    fun registerInstallStateUpdatedListener(listener: InstallStateUpdatedListener) {
        inAppUpdateManager.registerListener(listener)
    }

    fun unregisterInstallStateUpdatedListener(listener: InstallStateUpdatedListener) {
        inAppUpdateManager.unregisterListener(listener)
    }

    fun installAndRestart() {
        _installAndRestart.value = true
    }

    fun completeUpdate() {
        inAppUpdateManager.completeUpdate()
    }

    companion object {
        const val REQ_IN_APP_UPDATE = 944
    }
}
