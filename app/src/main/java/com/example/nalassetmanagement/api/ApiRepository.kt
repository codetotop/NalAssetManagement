package com.example.nalassetmanagement.api

import com.example.nalassetmanagement.model.LoginData
import retrofit2.Call

class ApiRepository {

    fun fetchLoginData(displayName: String, password: String): Call<LoginData>? {
        return ApiBuilder.getInstance()?.getApiService()?.login(
            displayName,
            password
        )
    }
}