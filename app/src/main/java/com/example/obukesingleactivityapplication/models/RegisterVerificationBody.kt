package com.example.obukesingleactivityapplication.models

import com.google.gson.annotations.SerializedName

data class RegisterVerificationBody(
    val email: String,
    val password: String,
    @SerializedName("verification_code")
    val verificationCode: String
)