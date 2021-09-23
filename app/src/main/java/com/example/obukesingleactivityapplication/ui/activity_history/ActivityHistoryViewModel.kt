package com.example.obukesingleactivityapplication.ui.activity_history

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.obukesingleactivityapplication.ApiInterface
import com.example.obukesingleactivityapplication.models.ActivityHistoryBody
import com.example.obukesingleactivityapplication.models.ActivityHistoryResponse
import com.example.obukesingleactivityapplication.models.ActivityItem
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ActivityHistoryViewModel : ViewModel() {

    private var _activityHistoryLiveData = MutableLiveData<ArrayList<ActivityItem>>()
    val activityHistoryLiveData = _activityHistoryLiveData

    fun getAllActivityHistory(bearerToken: String) {
        val call = ApiInterface.create().getActivityHistory("Bearer $bearerToken")
        call.enqueue(object : Callback<ActivityHistoryResponse> {
            override fun onResponse(
                call: Call<ActivityHistoryResponse>,
                response: Response<ActivityHistoryResponse>
            ) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        _activityHistoryLiveData.postValue(it.data.activities)
                    }
                }
            }

            override fun onFailure(call: Call<ActivityHistoryResponse>, t: Throwable) {
                Log.e("HISTORY", "${t.localizedMessage}")
            }
        }
        )
    }

    fun deleteActivity(bearerToken: String, activityItem: ActivityItem) {
        viewModelScope.launch {
            val response = ApiInterface.create().deleteActivity("Bearer $bearerToken", activityItem.id)
            if(response.isSuccessful){
                removeActivityFromList(activityItem)
            } else {
                Log.e("Err","Can't delete activity")
            }
        }
    }

    private fun removeActivityFromList(activityItem: ActivityItem){
        val newList = _activityHistoryLiveData.value
        newList?.let {
            it.remove(activityItem)
            _activityHistoryLiveData.postValue(it)
        }
    }
}