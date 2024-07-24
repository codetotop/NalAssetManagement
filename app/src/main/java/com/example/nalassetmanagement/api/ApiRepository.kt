package com.example.nalassetmanagement.api

import com.example.nalassetmanagement.model.AssetListResponse
import com.example.nalassetmanagement.model.LoginData
import retrofit2.Call

class ApiRepository {

    fun fetchLoginData(displayName: String, password: String): Call<LoginData>? {
        return ApiBuilder.getInstance()?.getApiService()?.login(
            displayName,
            password
        )
    }

    fun fetchAssetList(page: Int): Call<AssetListResponse>? {
        return ApiBuilder.getInstance()?.getApiService()?.fetchAssetList(page)
    }
}
