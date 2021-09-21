package com.example.obukesingleactivityapplication.models



data class ActivityHistoryResponse(
    val message: String,
    val data: ActivityHistoryData
)

data class ActivityHistoryData(
    val badges: List<BadgeItem>,
    val activities: ArrayList<ActivityItem>
)

data class ActivityItem(
    val id: Int,
    val title: String,
    val date: String
)

data class BadgeItem(
    val id: Int,
    val name: String,
    var isSelected: Boolean
)
