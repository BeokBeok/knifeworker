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

    private var baseWorkingHour: Float = 0F

    val setupStartWorkingHour = fun(startWorkingHour: Calendar) {
        _startWorkingTime.value = startWorkingHour
    }

    fun showWorkOffTime(workingHour: String) {
        if (!validWorkingHour(workingHour)) return

        var (workOffHour, workOffMinute) = calWorkOffTime(workingHour)
        if (checkStayUpAllNight(workOffHour)) return

        if (workOffHour > 12) workOffHour -= 12
        _result.value = Pair(workOffHour, (workOffMinute * 60).roundToInt())
    }

    fun setWorkingDay(day: Int) {
        baseWorkingHour = (FULL_TIME * day).toFloat()
    }

    private fun checkStayUpAllNight(workOffHour: Int): Boolean {
        if (workOffHour >= 24) {
            _err.value = IllegalStateException(R.string.msg_err_stay_up_all_night.toString())
            return true
        }
        return false
    }

    private fun calWorkOffTime(workingHour: String): Pair<Int, Float> {
        val time = baseWorkingHour - workingHour.toFloat()
        val (hour, minute) = time.toInt() to time - time.toInt()

        var workOffHour = _startWorkingTime.value!!.get(Calendar.HOUR_OF_DAY) + hour
        if (workOffHour >= 12) workOffHour += LAUNCH_TIME
        var workOffMinute = _startWorkingTime.value!!.get(Calendar.MINUTE) / 60.00f +
                minute
        if (workOffMinute >= 1) {
            workOffHour += 1
            workOffMinute -= 1
        }
        return Pair(workOffHour, workOffMinute)
    }

    private fun validWorkingHour(workingHour: String): Boolean {
        if (_startWorkingTime.value == null) {
            _err.value = IllegalStateException(R.string.msg_err_input_start_working_hour.toString())
            return false
        }
        if (workingHour.isEmpty()) {
            _err.value =
                IllegalStateException(R.string.msg_err_input_working_hour_until_current.toString())
            return false
        }
        if (baseWorkingHour == 0F) {
            _err.value = IllegalStateException(R.string.msg_err_check_day.toString())
            return false
        }
        if (workingHour.toFloat() >= baseWorkingHour) {
            _err.value =
                IllegalStateException(R.string.msg_err_exceed_working_hour.toString())
            return false
        }
        return true
    }

    companion object {
        const val LAUNCH_TIME = 1
        const val FULL_TIME = 8
    }
}