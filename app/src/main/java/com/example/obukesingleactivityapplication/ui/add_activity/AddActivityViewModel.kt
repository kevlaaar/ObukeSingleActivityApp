package com.example.obukesingleactivityapplication.ui.add_activity

import android.net.Uri
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.obukesingleactivityapplication.ApiInterface
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody

class AddActivityViewModel: ViewModel() {

    val addActivityStatus = MutableLiveData<Boolean?>(null)

    fun addActivity(bearerToken: String, title: String, date: String, photoPart: MultipartBody.Part) {
        val titleRequestBody = title.toRequestBody("text/plain".toMediaTypeOrNull())
        val dateRequestBody = date.toRequestBody("text/plain".toMediaTypeOrNull())

        viewModelScope.launch {
            val response = ApiInterface.create().addActivity("Bearer $bearerToken", titleRequestBody,dateRequestBody,photoPart )
            if(response.isSuccessful){
                addActivityStatus.postValue(true)
            } else {
                addActivityStatus.postValue(false)
            }
        }
    }

}