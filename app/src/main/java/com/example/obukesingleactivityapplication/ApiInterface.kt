package com.example.obukesingleactivityapplication

import com.example.obukesingleactivityapplication.models.*
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
            val client: OkHttpClient = OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .addInterceptor {
                    it.proceed(
                        it.request().newBuilder().addHeader("Accept", "application/json").build()
                    )
                }
                .build()

            val retrofit = Retrofit.Builder().addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .client(client)
                .build()

            return retrofit.create(ApiInterface::class.java)
        }
    }

    @POST("/api/auth/register")
    fun registerUser(@Body registerUserBody: RegisterUserBody): Call<UserResponse>

    @DELETE("/api/delete-activity")
    suspend fun deleteActivity(
        @Header("Authorization") bearerToken: String,
        @Query("id")activityId: Int): Response<DeleteResponse>

    @POST("/api/auth/login")
    suspend fun login(
        @Query("email") email: String,
        @Query("password") password: String
    ): Response<UserResponse>

    @POST("/api/auth/verify")
    fun verifyUser(@Body registerVerificationBody: RegisterVerificationBody): Call<UserResponse>

    @POST("/api/auth/logout")
    suspend fun logout(@Header("Authorization") bearerToken: String): Response<LogoutResponse>

    @POST("/api/auth/refresh")
    fun refreshToken(@Header("Authorization") bearerToken: String): Call<UserResponse>

    @GET("/api/activity-history")
    fun getActivityHistory(
        @Header("Authorization") bearerToken: String,
        @Query("badge_type_id") badgeTypeId: Int = 0,
        @Query("badge_id") badgeId: Int = 0
    ): Call<ActivityHistoryResponse>


}