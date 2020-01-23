package com.beok.knifeworker

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.beok.knifeworker.databinding.ActivityMainBinding
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
    }

    private fun setupBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.lifecycleOwner = this
        binding.vm = viewModel
    }

    private fun setupObserver() {
        viewModel.startWorkingHour.observe(this, Observer { datetime ->
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
    }
}
