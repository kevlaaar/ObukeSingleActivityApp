package com.example.obukesingleactivityapplication.ui.greetings

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.obukesingleactivityapplication.ApiInterface
import com.example.obukesingleactivityapplication.models.LogoutResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GreetingsViewModel: ViewModel() {

    val logoutStatus = MutableLiveData<Boolean?>(null)

    fun logout(bearerToken: String) {
        val call = ApiInterface.create().logout("Bearer $bearerToken")
        call.enqueue(object: Callback<LogoutResponse>{
            override fun onResponse(call: Call<LogoutResponse>, response: Response<LogoutResponse>) {
                if(response.isSuccessful){
                    logoutStatus.postValue(true)
                } else {
                    logoutStatus.postValue(false)
                }
            }

            override fun onFailure(call: Call<LogoutResponse>, t: Throwable) {
                logoutStatus.postValue(false)
            }
        })

    }

}