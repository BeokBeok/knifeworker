package com.beok.knifeworker

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.util.*
import kotlin.math.roundToInt

class MainViewModel : ViewModel() {

    private val _startWorkingTime = MutableLiveData<Calendar>()
    val startWorkingTime: LiveData<Calendar> get() = _startWorkingTime

    private val _err = MutableLiveData<Throwable>()
    val err: LiveData<Throwable> get() = _err

    private val _result = MutableLiveData<Pair<Int, Int>>()
    val result: LiveData<Pair<Int, Int>> get() = _result

    val setupStartWorkingHour = fun(startWorkingHour: Calendar) {
        _startWorkingTime.value = startWorkingHour
    }

    fun showWorkOffTime(remainWorkingHour: String) {
        if (checkValid(remainWorkingHour)) return

        val (workOffHour, workOffMinute) = calculateWorkOffTime(remainWorkingHour)
        _result.value = Pair(workOffHour - 12, (workOffMinute * 60).roundToInt())
    }

    private fun calculateWorkOffTime(remainWorkingHour: String): Pair<Int, Float> {
        val isStartWorkingPM = _startWorkingTime.value!!.get(Calendar.AM_PM) == 1
        val remainHour = remainWorkingHour.toFloat().toInt()
        val remainMinute = remainWorkingHour.toFloat() - remainHour

        var workOffHour = if (!isStartWorkingPM) {
            _startWorkingTime.value!!.get(Calendar.HOUR) + remainHour + LAUNCH_TIME
        } else {
            _startWorkingTime.value!!.get(Calendar.HOUR) + remainHour
        }
        var workOffMinute = _startWorkingTime.value!!.get(Calendar.MINUTE) / 60.00f +
                remainMinute
        if (workOffMinute > 1) {
            workOffHour += 1
            workOffMinute -= 1
        }
        return Pair(workOffHour, workOffMinute)
    }

    private fun checkValid(remainWorkingHour: String): Boolean {
        if (_startWorkingTime.value == null) {
            _err.value = IllegalStateException(R.string.msg_err_input_start_working_hour.toString())
            return true
        }
        if (remainWorkingHour.isEmpty()) {
            _err.value =
                IllegalStateException(R.string.msg_err_input_remain_working_hour.toString())
            return true
        }
        return false
    }

    companion object {
        const val LAUNCH_TIME = 1
    }
}