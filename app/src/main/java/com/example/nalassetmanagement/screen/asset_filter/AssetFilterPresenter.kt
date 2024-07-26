package com.example.nalassetmanagement.screen.asset_filter

import com.example.nalassetmanagement.api.ApiRepository
import com.example.nalassetmanagement.model.AssetListResponse
import com.example.nalassetmanagement.model.FilterListResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AssetFilterPresenter(private val view: AssetFilterContract.View) : AssetFilterContract.Presenter {
    private val apiRepository: ApiRepository = ApiRepository()

    override fun fetchData() {
        apiRepository.fetchFilterList()?.enqueue(object : Callback<FilterListResponse> {
            override fun onResponse(call: Call<FilterListResponse>, response: Response<FilterListResponse>) {
                response.body()?.let { body ->
                    if (response.isSuccessful) {
                        view.fetchFilterListSuccess(body.data)
                    } else {
                        view.fetchFilterListFailure()
                    }
                }
            }

            override fun onFailure(call: Call<FilterListResponse>, t: Throwable) {
                view.fetchFilterListFailure()
            }
        })
    }
}
