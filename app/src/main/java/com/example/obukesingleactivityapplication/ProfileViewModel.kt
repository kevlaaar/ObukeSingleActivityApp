package com.example.obukesingleactivityapplication

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.obukesingleactivityapplication.models.RegisterUserBody
import com.example.obukesingleactivityapplication.models.UserResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProfileViewModel: ViewModel() {

    val shouldShowWarning = MutableLiveData<Boolean?>(false)


    val warningMessage = MutableLiveData<String?>(null)


    var message: String? = null




}