package com.example.nalassetmanagement.screen.asset_list

import com.example.nalassetmanagement.api.ApiRepository
import com.example.nalassetmanagement.model.AssetListResponse
import com.example.nalassetmanagement.model.LoginData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AssetListPresenter(private val mainView: AssetListContract.View) : AssetListContract.Presenter {
    private val apiRepository: ApiRepository = ApiRepository()

    override fun login(displayName: String, password: String) {
        apiRepository.fetchLoginData(displayName, password)?.enqueue(object : Callback<LoginData> {
            override fun onResponse(call: Call<LoginData>, response: Response<LoginData>) {
                response.body()?.let { body ->
                    if (response.isSuccessful && body.status == 200) {
                        mainView.loginSuccess(body.data)
                    } else {
                        mainView.loginFailure()
                    }
                }
            }

            override fun onFailure(call: Call<LoginData>, t: Throwable) {
                mainView.loginFailure()
            }
        })
    }

    override fun fetchAssetList(page: Int) {
        apiRepository.fetchAssetList(page)?.enqueue(object : Callback<AssetListResponse> {
            override fun onResponse(call: Call<AssetListResponse>, response: Response<AssetListResponse>) {
                response.body()?.let { body ->
                    if (response.isSuccessful) {
                        mainView.fetchAssetListSuccess(body.data)
                    } else {
                        mainView.fetchAssetListFailure()
                    }
                }
            }

            override fun onFailure(call: Call<AssetListResponse>, t: Throwable) {
                mainView.fetchAssetListFailure()
            }
        })
    }
}
