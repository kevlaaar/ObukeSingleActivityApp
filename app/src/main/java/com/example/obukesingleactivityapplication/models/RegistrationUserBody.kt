package com.example.obukesingleactivityapplication.models

import com.google.gson.annotations.SerializedName

data class RegisterUserBody(
    @SerializedName("first_name")
    val firstName: String,
    @SerializedName("last_name")
    val lastName: String,
    val email: String,
    val password: String,
    @SerializedName("password_confirmation")
    val repeatPassword: String,
    @SerializedName("country_id")
    val countryId: Int = 1,
    @SerializedName("birth_year")
    val birthYear: String
)