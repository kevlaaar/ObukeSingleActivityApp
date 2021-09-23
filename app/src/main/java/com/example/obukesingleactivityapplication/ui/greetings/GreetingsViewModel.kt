package com.example.obukesingleactivityapplication.ui.greetings

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.obukesingleactivityapplication.ApiInterface
import com.example.obukesingleactivityapplication.models.LogoutResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GreetingsViewModel : ViewModel() {

    val logoutStatus = MutableLiveData<Boolean?>(null)

    fun logout(bearerToken: String) {
        viewModelScope.launch {
            val response = ApiInterface.create().logout("Bearer $bearerToken")
            if (response.isSuccessful) {
                logoutStatus.postValue(true)
            } else {
                logoutStatus.postValue(false)
            }
        }
    }
}
