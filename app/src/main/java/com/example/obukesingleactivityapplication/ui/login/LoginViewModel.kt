package com.example.obukesingleactivityapplication.ui.login

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.obukesingleactivityapplication.ApiInterface
import com.example.obukesingleactivityapplication.models.User
import com.example.obukesingleactivityapplication.models.UserResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginViewModel: ViewModel() {

    val loginStatus = MutableLiveData<Boolean?>(null)
    val verificationStatus = MutableLiveData<Boolean?>(null)
    val userResponseLiveData = MutableLiveData<UserResponse?>(null)
    val bearerTokenLiveData = MutableLiveData<String?>(null)

    val refreshTokenUserLiveData = MutableLiveData<User?>(null)
    val refreshTokenStatus = MutableLiveData<Boolean?>(null)


    fun refreshToken(bearerToken: String) {
        val call = ApiInterface.create().refreshToken("Bearer $bearerToken")
        call.enqueue(object: Callback<UserResponse>{
            override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                if(response.isSuccessful){
                    response.body()?.let{
                        refreshTokenUserLiveData.postValue(it.user)
                        refreshTokenStatus.postValue(true)
                    }
                } else {
                    refreshTokenUserLiveData.postValue(null)
                    refreshTokenStatus.postValue(false)
                }
            }

            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                refreshTokenUserLiveData.postValue(null)
                refreshTokenStatus.postValue(false)
            }

        })
    }

    fun login(email: String, password: String){
        val call = ApiInterface.create().login(email, password)
        call.enqueue(object: Callback<UserResponse> {
            override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                Log.e("response", "${response.isSuccessful}, ${response.body()}, ${response.errorBody()} ${response.message()} " +
                        "${response.code()}")
                if(response.isSuccessful){
                    loginStatus.postValue(true)
                    response.body()?.let {
                        bearerTokenLiveData.postValue(it.accessToken)
                        userResponseLiveData.postValue(it)
                    }
                } else {
                    if(response.code() == 422) {
                        verificationStatus.postValue(true)
                    } else {
                        loginStatus.postValue(false)
                    }
                }
            }

            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                loginStatus.postValue(null)
            }
        })
    }

}