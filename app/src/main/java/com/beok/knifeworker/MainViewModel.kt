package com.beok.knifeworker

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.util.*

class MainViewModel : ViewModel() {

    private val _startWorkingHour = MutableLiveData<Calendar>()
    val startWorkingHour: LiveData<Calendar> get() = _startWorkingHour

    val setupStartWorkingHour = fun(startWorkingHour: Calendar) {
        _startWorkingHour.value = startWorkingHour
    }
}