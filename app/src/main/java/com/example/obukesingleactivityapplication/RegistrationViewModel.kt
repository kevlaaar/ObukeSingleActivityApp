package com.example.obukesingleactivityapplication

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.obukesingleactivityapplication.models.RegisterUserBody
import com.example.obukesingleactivityapplication.models.UserResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegistrationViewModel: ViewModel() {


    val registrationStatus = MutableLiveData<Boolean?>(null)


    fun registerUser(registrationUserBody: RegisterUserBody) {
        val call = ApiInterface.create().registerUser(registrationUserBody)
        call.enqueue(object: Callback<UserResponse> {
            override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                registrationStatus.postValue(true)
            }

            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                Log.e("ERROR: ", t.toString())
                registrationStatus.postValue(false)
            }
        })
    }
}