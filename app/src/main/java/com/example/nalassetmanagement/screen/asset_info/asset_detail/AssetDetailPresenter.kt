package com.example.nalassetmanagement.screen.asset_info.asset_detail

import com.example.nalassetmanagement.api.ApiRepository
import com.example.nalassetmanagement.model.server.FilterListResponse
import com.example.nalassetmanagement.model.server.UpdateStatusResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AssetDetailPresenter(private val view: AssetDetailContract.View) :
    AssetDetailContract.Presenter {
    private val apiRepository: ApiRepository = ApiRepository()

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
}
