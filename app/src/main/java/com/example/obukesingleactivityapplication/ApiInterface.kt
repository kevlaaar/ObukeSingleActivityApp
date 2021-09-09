package com.example.obukesingleactivityapplication

import com.example.obukesingleactivityapplication.models.RegisterUserBody
import com.example.obukesingleactivityapplication.models.UserResponse
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

interface ApiInterface {

    companion object {
        //563492ad6f917000010000019836f01db83b43b280b1062c709a375a
        var BASE_URL = "https://credspo-dev.amplitudo.me/"
        fun create(): ApiInterface {
            val interceptor = HttpLoggingInterceptor()
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
            val client: OkHttpClient = OkHttpClient.Builder().addInterceptor(interceptor).build()

            val retrofit = Retrofit.Builder().
            addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .client(client)
                .build()

            return retrofit.create(ApiInterface::class.java)
        }
    }

    @POST("/api/auth/register")
    fun registerUser (@Body registerUserBody: RegisterUserBody): Call<UserResponse>




}