package com.example.nalassetmanagement.model

import com.google.gson.annotations.SerializedName

class FilterListResponse : BaseResponse<FilterList>()

data class FilterList(
    @SerializedName("category")
    val category: List<KeyValue>,
    @SerializedName("address")
    val address: List<KeyValue>,
    @SerializedName("models")
    val models: List<KeyValue>,
    @SerializedName("producer")
    val producer: List<KeyValue>,
    @SerializedName("status")
    val status: List<KeyValue>,
    @SerializedName("user")
    val user: List<User>
)

data class User(
    @SerializedName("id")
    val id: Int? = null,
    @SerializedName("name")
    val username: String? = null
)
