package com.example.coverbackgroundsolution

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CoroutineViewModel : ViewModel() {
    var progress = MutableLiveData<Int>(0)

}