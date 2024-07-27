package com.example.nalassetmanagement.model.server

import com.google.gson.annotations.SerializedName

abstract class BaseResponse<T> {
    @SerializedName("data")
    private var mData: T? = null

    @SerializedName("response_code")
    var responseCode: Int = 0

    @SerializedName("message")
    var message: String? = null

    var data: T?
        get() = mData
        set(data) {
            mData = data
        }
}
