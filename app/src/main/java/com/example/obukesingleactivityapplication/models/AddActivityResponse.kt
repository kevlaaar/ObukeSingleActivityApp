package com.example.obukesingleactivityapplication.models

import com.google.gson.annotations.SerializedName

data class AddActivityResponse (
    val message: String,
    val data: List<ActivityResponseData>
)

data class ActivityResponseData(
    val id: Int,
    val title: String,
    val description: String?,
    val date: String,
    val organization: String?,
    val countryId: Int,
    val skills: List<BadgeItemResponse>,
    val attributes: List<BadgeItemResponse>,
    val waypoints: List<BadgeItemResponse>,
    val emotions: List<EmotionItemResponse>,
    @SerializedName("evidence_links")
    val evidenceLinks: Array<String>,
    @SerializedName("evidence_images")
    val evidenceImages: Array<String>
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ActivityResponseData

        if (id != other.id) return false
        if (title != other.title) return false
        if (description != other.description) return false
        if (date != other.date) return false
        if (organization != other.organization) return false
        if (skills != other.skills) return false
        if (attributes != other.attributes) return false
        if (waypoints != other.waypoints) return false
        if (emotions != other.emotions) return false
        if (!evidenceLinks.contentEquals(other.evidenceLinks)) return false
        if (!evidenceImages.contentEquals(other.evidenceImages)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id
        result = 31 * result + title.hashCode()
        result = 31 * result + description.hashCode()
        result = 31 * result + date.hashCode()
        result = 31 * result + organization.hashCode()
        result = 31 * result + skills.hashCode()
        result = 31 * result + attributes.hashCode()
        result = 31 * result + waypoints.hashCode()
        result = 31 * result + emotions.hashCode()
        result = 31 * result + evidenceLinks.contentHashCode()
        result = 31 * result + evidenceImages.contentHashCode()
        return result
    }
}

data class BadgeItemResponse(
    @SerializedName("badge_id")
    val badgeId: Int,
    @SerializedName("badge_name")
    val badgeName: String
)

data class EmotionItemResponse(
    @SerializedName("emotion_id")
    val emotionId: Int,
    @SerializedName("emotion_name")
    val emotionName: String
)
