package com.example.obukesingleactivityapplication

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ProfileViewModel: ViewModel() {

    val shouldShowWarning = MutableLiveData<Boolean?>(false)


    val warningMessage = MutableLiveData<String?>(null)


    var message: String? = null


}