package com.beok.knifeworker.inapp

import android.app.Activity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.android.play.core.appupdate.AppUpdateInfo
import com.google.android.play.core.appupdate.AppUpdateManager
import com.google.android.play.core.install.model.AppUpdateType
import com.google.android.play.core.install.model.UpdateAvailability
import javax.inject.Inject

class InAppUpdateManager @Inject constructor(
    private val inAppUpdateManager: AppUpdateManager
) {

    private val _appUpdatable = MutableLiveData<InAppUpdateType>()
    val appUpdatable: LiveData<InAppUpdateType> get() = _appUpdatable

    fun checkAppUpdatable() {
        inAppUpdateManager.appUpdateInfo
            .addOnSuccessListener { appUpdateInfo ->
                when {
                    appUpdateInfo.updateAvailability() != UpdateAvailability.UPDATE_AVAILABLE ->
                        _appUpdatable.value = InAppUpdateType.Impossible
                    appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.FLEXIBLE) -> {
                        _appUpdatable.value = InAppUpdateType.Possible(
                            type = AppUpdateType.FLEXIBLE,
                            info = appUpdateInfo
                        )
                    }
                    appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.IMMEDIATE) -> {
                        _appUpdatable.value = InAppUpdateType.Possible(
                            type = AppUpdateType.IMMEDIATE,
                            info = appUpdateInfo
                        )
                    }
                }
            }
            .addOnFailureListener {
                _appUpdatable.value = InAppUpdateType.Impossible
            }
    }

    fun registerUpdateFlowForResult(
        appUpdateInfo: AppUpdateInfo,
        appUpdateType: Int,
        target: Activity
    ) {
        inAppUpdateManager.startUpdateFlowForResult(
            appUpdateInfo,
            appUpdateType,
            target,
            REQ_IN_APP_UPDATE
        )
    }
    
    companion object {
        const val REQ_IN_APP_UPDATE = 944
    }
}
