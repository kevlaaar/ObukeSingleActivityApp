package com.example.obukesingleactivityapplication.models

import com.google.gson.annotations.SerializedName

data class ActivityHistoryBody(
    @SerializedName("badge_type_id")
    val badgeTypeId: Int = 0,
    @SerializedName("badge_id")
    val badgeId: Int = 0
)
