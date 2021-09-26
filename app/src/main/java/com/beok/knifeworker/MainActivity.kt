package com.beok.knifeworker

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.beok.knifeworker.databinding.ActivityMainBinding
import com.beok.knifeworker.inapp.InAppUpdateManager
import com.beok.knifeworker.inapp.InAppUpdateType
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.MobileAds
import com.google.android.material.snackbar.Snackbar
import com.google.android.play.core.install.InstallStateUpdatedListener
import com.google.android.play.core.install.model.InstallStatus
import com.google.android.play.core.review.ReviewManagerFactory
import dagger.hilt.android.AndroidEntryPoint
import java.util.*
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val viewModel by viewModels<MainViewModel>()

    @Inject
    lateinit var inAppUpdateManager: InAppUpdateManager

    private lateinit var installStateUpdatedListener: InstallStateUpdatedListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupBinding()
        setupListener()
        setupObserver()
        setupAdmob()
        setupInAppUpdate()
    }

    @Suppress("Deprecation")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode != InAppUpdateManager.REQ_IN_APP_UPDATE) return
        if (resultCode != Activity.RESULT_OK) {
            Toast.makeText(this, getString(R.string.cancel_update), Toast.LENGTH_SHORT)
                .show()
            inAppUpdateManager.unregisterInstallStateUpdatedListener(installStateUpdatedListener)
        }
    }

    private fun setupListener() {
        installStateUpdatedListener = InstallStateUpdatedListener {
            if (it.installStatus() == InstallStatus.DOWNLOADED) {
                Snackbar.make(
                    binding.clMain,
                    getString(R.string.complete_download_for_update),
                    5_000
                ).setAction(getString(R.string.install_and_restart)) {
                    inAppUpdateManager.installAndRestart()
                }.show()
            }
        }
        inAppUpdateManager.registerInstallStateUpdatedListener(installStateUpdatedListener)
    }

    private fun setupInAppUpdate() {
        inAppUpdateManager.checkAppUpdatable()
    }

    private fun setupAdmob() {
        MobileAds.initialize(this)
        binding.adview.loadAd(AdRequest.Builder().build())
    }

    private fun setupBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.lifecycleOwner = this
        binding.vm = viewModel
    }

    private fun setupObserver() {
        observeViewModel()
        observeInAppUpdate()
    }

    private fun observeViewModel() {
        val owner = this@MainActivity
        viewModel.run {
            startWorkingTime.observe(owner) { datetime ->
                val amOrPm = if (datetime.get(Calendar.AM_PM) == 1) {
                    getString(R.string.pm)
                } else {
                    getString(R.string.am)
                }
                binding.tvStartWorking.text = String.format(
                    getString(R.string.contents_start_working),
                    amOrPm,
                    datetime.get(Calendar.HOUR),
                    datetime.get(Calendar.MINUTE)
                )
            }
            err.observe(owner) {
                val stringRes = it.message?.toInt() ?: -1
                Toast.makeText(owner, getString(stringRes), Toast.LENGTH_SHORT).show()
                binding.tvResult.text = ""
                hideKeyboard()
            }
            result.observe(owner) {
                binding.tvResult.text = if (it.first == 0 && it.second == 0) ""
                else String.format(getString(R.string.msg_result_work_off), it.first, it.second)
                hideKeyboard()
                inAppReview()
            }
        }
    }

    private fun observeInAppUpdate() {
        val owner = this@MainActivity
        inAppUpdateManager.run {
            appUpdatable.observe(owner) {
                val inAppUpdateType = it.getContentIfNotHandled() ?: return@observe
                when (inAppUpdateType) {
                    is InAppUpdateType.Impossible -> {
                        if (!::installStateUpdatedListener.isInitialized) return@observe
                        unregisterInstallStateUpdatedListener(installStateUpdatedListener)
                    }
                    is InAppUpdateType.Possible -> registerUpdateFlowForResult(
                        appUpdateInfo = inAppUpdateType.info,
                        appUpdateType = inAppUpdateType.type,
                        target = owner
                    )
                }.javaClass
            }
            installAndRestart.observe(owner) { tryInstallAndRestart ->
                if (!tryInstallAndRestart) return@observe
                completeUpdate()
                unregisterInstallStateUpdatedListener(listener = installStateUpdatedListener)
            }
        }
    }

    private fun hideKeyboard() {
        (getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager).hideSoftInputFromWindow(
            binding.tietTotalWorkingHour.windowToken,
            0
        )
    }

    private fun inAppReview() {
        val reviewManager = ReviewManagerFactory.create(this)
        val requestReviewFlow = reviewManager.requestReviewFlow()
        requestReviewFlow.addOnCompleteListener {
            if (!it.isSuccessful) return@addOnCompleteListener
            reviewManager.launchReviewFlow(this, it.result).addOnCompleteListener {
                // NO OP
            }
        }
    }
}
