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
import androidx.lifecycle.Observer
import com.beok.knifeworker.databinding.ActivityMainBinding
import com.beok.knifeworker.inapp.InAppUpdateManager
import com.beok.knifeworker.inapp.InAppUpdateType
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.MobileAds
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupBinding()
        setupObserver()
        setupAdmob()
        setupInAppUpdate()
    }

    private fun setupInAppUpdate() {
        inAppUpdateManager.checkAppUpdatable()
    }

    @Suppress("Deprecation")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode != InAppUpdateManager.REQ_IN_APP_UPDATE) return
        if (resultCode != Activity.RESULT_OK) {
            Toast.makeText(this, getString(R.string.cancel_update), Toast.LENGTH_SHORT)
                .show()
        }
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
        inAppUpdateManager.appUpdatable.observe(owner) { inAppUpdateType ->
            when (inAppUpdateType) {
                is InAppUpdateType.Impossible -> return@observe
                is InAppUpdateType.Possible -> inAppUpdateManager.registerUpdateFlowForResult(
                    appUpdateInfo = inAppUpdateType.info,
                    appUpdateType = inAppUpdateType.type,
                    target = owner
                )
            }.javaClass
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
