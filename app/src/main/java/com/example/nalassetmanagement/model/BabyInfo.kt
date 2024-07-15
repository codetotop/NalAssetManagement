package com.example.nalassetmanagement.model

import com.google.gson.annotations.SerializedName

data class BabyInfo(
  @SerializedName("id") val id: String,
  @SerializedName("birthday") val birthday: Long
)