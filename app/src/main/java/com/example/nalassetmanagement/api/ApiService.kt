package com.example.nalassetmanagement.api

import com.example.nalassetmanagement.model.LoginData
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface ApiService {

    @FormUrlEncoded
    @POST("api/v1/auth/login")
    fun login(@Field("display_name") displayName: String, @Field("password") password: String): Call<LoginData>
}