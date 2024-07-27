package com.example.nalassetmanagement.api

import com.example.nalassetmanagement.model.server.AssetListResponse
import com.example.nalassetmanagement.model.server.FilterListResponse
import com.example.nalassetmanagement.model.LoginData
import com.example.nalassetmanagement.model.server.AssetDetailResponse
import com.example.nalassetmanagement.model.server.BaseResponse
import com.example.nalassetmanagement.model.server.UpdateStatusResponse
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

    fun fetchFilterList(): Call<FilterListResponse>? {
        return ApiBuilder.getInstance()?.getApiService()?.fetchFilterList()
    }

    fun fetchAssetDetail(id: Int): Call<AssetDetailResponse>? {
        return ApiBuilder.getInstance()?.getApiService()?.fetchAssetDetail(id)
    }

    fun updateAssetDetail(id: Int, type: String, key: String, value: String): Call<UpdateStatusResponse>? {
        return ApiBuilder.getInstance()?.getApiService()?.updateAssetDetail(id, type, key, value)
    }
}
