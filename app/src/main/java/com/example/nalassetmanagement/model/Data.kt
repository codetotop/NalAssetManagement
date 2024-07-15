package com.example.nalassetmanagement.model

import com.example.nalassetmanagement.model.User
import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("token") val token: String,
    @SerializedName("exp") val exp: Long,
    @SerializedName("user") val user: User,
)