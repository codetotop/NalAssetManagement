package com.example.nalassetmanagement.model

import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("id") val id: Int,
    @SerializedName("display_name") val displayName: String,
    @SerializedName("avatar")val avatar: String,
    @SerializedName("verified")val verified: Int,
    @SerializedName("type_mom")val typeMom: String,
    @SerializedName("baby_info")val listBaby: List<BabyInfo>,
)