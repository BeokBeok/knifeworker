package com.beok.knifeworker

import android.os.Build
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config
import java.util.*

@RunWith(AndroidJUnit4::class)
@Config(sdk = [Build.VERSION_CODES.O])
class MainViewModelTest {

    private lateinit var viewModel: MainViewModel

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        viewModel = MainViewModel()
    }

    @Test
    fun `총 근무시간이 일차에 비해 턱없이 부족할 경우`() {
        viewModel.run {
            setWorkingDay(5)
            setupStartWorkingHour(Calendar.getInstance().apply {
                set(Calendar.HOUR_OF_DAY, 10)
                set(Calendar.MINUTE, 0)
            })
            showWorkOffTime("0")
            assertEquals(err.value?.message, R.string.msg_err_stay_up_all_night.toString())
        }
    }
}