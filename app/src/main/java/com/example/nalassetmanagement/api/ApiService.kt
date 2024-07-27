package com.example.nalassetmanagement.api

import com.example.nalassetmanagement.model.server.AssetListResponse
import com.example.nalassetmanagement.model.server.FilterListResponse
import com.example.nalassetmanagement.model.LoginData
import com.example.nalassetmanagement.model.server.AssetDetailResponse
import com.example.nalassetmanagement.model.server.BaseResponse
import com.example.nalassetmanagement.model.server.UpdateStatusResponse
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @FormUrlEncoded
    @POST("api/v1/auth/login")
    fun login(
        @Field("display_name") displayName: String,
        @Field("password") password: String
    ): Call<LoginData>

    @GET("api/product/list?")
    fun fetchAssetList(@Query("page") page: Int): Call<AssetListResponse>

    @POST("api/option/list")
    fun fetchFilterList(): Call<FilterListResponse>

    @GET("api/product/detail/{id}")
    fun fetchAssetDetail(@Path("id") id: Int): Call<AssetDetailResponse>

    @FormUrlEncoded
    @POST("api/product/update/{id}")
    fun updateAssetDetail(
        @Path("id") id: Int,
        @Field("type") type: String,
        @Field("key") key: String,
        @Field("value") value: String
    ): Call<UpdateStatusResponse>
}
