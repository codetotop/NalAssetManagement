package com.example.nalassetmanagement.api

import com.example.nalassetmanagement.model.AssetListResponse
import com.example.nalassetmanagement.model.FilterListResponse
import com.example.nalassetmanagement.model.LoginData
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {

    @FormUrlEncoded
    @POST("api/v1/auth/login")
    fun login(@Field("display_name") displayName: String, @Field("password") password: String): Call<LoginData>

    @GET("api/product/list?")
    fun fetchAssetList(@Query("page") page: Int): Call<AssetListResponse>

    @POST("api/option/list")
    fun fetchFilterList(): Call<FilterListResponse>
}
