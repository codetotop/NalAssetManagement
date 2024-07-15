package com.example.nalassetmanagement.model

import com.google.gson.annotations.SerializedName

data class LoginData(
  @SerializedName("status") val status: Int,
  @SerializedName("message") val message: String,
  @SerializedName("data") val data: Data
)