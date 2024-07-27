package com.example.nalassetmanagement.screen.asset_info.asset_detail

import com.example.nalassetmanagement.api.ApiRepository
import com.example.nalassetmanagement.model.server.AssetDetailResponse
import com.example.nalassetmanagement.model.server.FilterListResponse
import com.example.nalassetmanagement.model.server.UpdateQrCodeResponse
import com.example.nalassetmanagement.model.server.UpdateStatusResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AssetDetailPresenter(private val view: AssetDetailContract.View) :
    AssetDetailContract.Presenter {
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
        apiRepository.fetchFilterList()?.enqueue(object : Callback<FilterListResponse> {
            override fun onResponse(
                call: Call<FilterListResponse>,
                response: Response<FilterListResponse>
            ) {
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

    override fun updateStatusAsset(id: Int, type: String, key: String, value: String) {
        apiRepository.updateAssetDetail(id, type, key, value)
            ?.enqueue(object : Callback<UpdateStatusResponse> {
                override fun onResponse(
                    call: Call<UpdateStatusResponse>,
                    response: Response<UpdateStatusResponse>
                ) {

                    response.body()?.let { body ->
                        if (response.isSuccessful) {
                            view.updateStatusSuccess()
                        } else {
                            view.updateStatusFailure()
                        }
                    }
                }

                override fun onFailure(p0: Call<UpdateStatusResponse>, p1: Throwable) {
                    view.updateStatusFailure()
                }

            })
    }

    override fun updateQrCodeAsset(id: Int, type: String, value: String) {
        apiRepository.updateAssetDetail(id, type, "", value)
            ?.enqueue(object : Callback<UpdateStatusResponse> {
                override fun onResponse(
                    call: Call<UpdateStatusResponse>,
                    response: Response<UpdateStatusResponse>
                ) {

                    response.body()?.let { body ->
                        if (response.isSuccessful) {
                            view.updateQrCodeSuccess(value)
                        } else {
                            view.updateQrCodeFailure()
                        }
                    }
                }

                override fun onFailure(p0: Call<UpdateStatusResponse>, p1: Throwable) {
                    view.updateQrCodeFailure()
                }

            })
    }
}
