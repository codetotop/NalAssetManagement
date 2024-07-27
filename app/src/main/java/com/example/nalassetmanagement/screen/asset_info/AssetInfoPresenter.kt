package com.example.nalassetmanagement.screen.asset_info

import com.example.nalassetmanagement.api.ApiRepository
import com.example.nalassetmanagement.model.server.AssetDetailResponse
import com.example.nalassetmanagement.model.server.AssetListResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AssetInfoPresenter(private val view: AssetInfoContract.View) : AssetInfoContract.Presenter {

    private val apiRepository: ApiRepository = ApiRepository()

    override fun fetchAssetDetail(id: Int) {
        apiRepository.fetchAssetDetail(id)?.enqueue(object : Callback<AssetDetailResponse> {
            override fun onResponse(
                call: Call<AssetDetailResponse>,
                response: Response<AssetDetailResponse>
            ) {
                response.body()?.let { body ->
                    if (response.isSuccessful) {
                        view.fetchAssetDetailSuccess(body.data)
                    } else {
                        view.fetchAssetDetailFailure()
                    }
                }
            }

            override fun onFailure(p0: Call<AssetDetailResponse>, p1: Throwable) {
                view.fetchAssetDetailFailure()
            }
        })
    }

    override fun fetchFilterList() {

    }
}
