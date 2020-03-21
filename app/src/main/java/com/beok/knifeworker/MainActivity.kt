package com.beok.knifeworker

import android.content.Context
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.beok.knifeworker.databinding.ActivityMainBinding
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.MobileAds
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val viewModel by lazy {
        ViewModelProvider(this, object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel?> create(modelClass: Class<T>): T = MainViewModel() as T
        })[MainViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupBinding()
        setupObserver()
        setupAdmob()
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
            startWorkingTime.observe(owner, Observer { datetime ->
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
            })
            err.observe(owner, Observer {
                val stringRes = it.message?.toInt() ?: -1
                Toast.makeText(owner, getString(stringRes), Toast.LENGTH_SHORT).show()
                binding.tvResult.text = ""
                hideKeyboard()
            })
            result.observe(owner, Observer {
                binding.tvResult.text = if (it.first == 0 && it.second == 0) ""
                else String.format(getString(R.string.msg_result_work_off), it.first, it.second)
                hideKeyboard()
            })
        }
    }

    private fun hideKeyboard() {
        (getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager).hideSoftInputFromWindow(
            binding.tietTotalWorkingHour.windowToken,
            0
        )
    }
}
