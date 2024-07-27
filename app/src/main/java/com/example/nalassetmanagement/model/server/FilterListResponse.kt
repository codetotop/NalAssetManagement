package com.example.nalassetmanagement.model.server

import com.google.gson.annotations.SerializedName
import java.io.Serializable

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
): Serializable
