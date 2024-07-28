package com.example.nalassetmanagement.screen.asset_list

import android.content.Context
import com.example.nalassetmanagement.api.ApiRepository
import com.example.nalassetmanagement.model.server.AssetListResponse
import com.example.nalassetmanagement.model.LoginData
import com.example.nalassetmanagement.model.server.Asset
import com.example.nalassetmanagement.room.database.importDataFakeToDatabase
import com.example.nalassetmanagement.room.database.importDataToDatabase
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AssetListPresenter(private val view: AssetListContract.View) : AssetListContract.Presenter {
    private val apiRepository: ApiRepository = ApiRepository()

    override fun login(displayName: String, password: String) {
        apiRepository.fetchLoginData(displayName, password)?.enqueue(object : Callback<LoginData> {
            override fun onResponse(call: Call<LoginData>, response: Response<LoginData>) {
                response.body()?.let { body ->
                    if (response.isSuccessful && body.status == 200) {
                        view.loginSuccess(body.data)
                    } else {
                        view.loginFailure()
                    }
                }
            }

            override fun onFailure(call: Call<LoginData>, t: Throwable) {
                view.loginFailure()
            }
        })
    }

    override fun fetchAssetList(page: Int) {
        apiRepository.fetchAssetList(page)?.enqueue(object : Callback<AssetListResponse> {
            override fun onResponse(
                call: Call<AssetListResponse>,
                response: Response<AssetListResponse>
            ) {
                response.body()?.let { body ->
                    if (response.isSuccessful) {
                        view.fetchAssetListSuccess(body.data)
                        importData(body.data?.data)
                    } else {
                        view.fetchAssetListFailure()
                    }
                }
            }

            override fun onFailure(call: Call<AssetListResponse>, t: Throwable) {
                view.fetchAssetListFailure()
            }
        })
    }
    private fun importData(listAssetEntity: List<Asset>?) {
        try {
            if (listAssetEntity != null) {
                importDataToDatabase(
                    context = (view as Context).applicationContext,
                    listAssetEntity.map { it.toAssetEntity() }
                )
            }
        }
        catch (_ : Exception) {}
    }

    override fun searchQr(qrText: String, assetListResponses: List<Asset>) {
        var asset: Asset? = null
        for (assetTemp in assetListResponses) {
            if (qrText.trim() == assetTemp.qrCode)  asset = assetTemp
        }

        if (asset == null) {
            view.searchQrFailure()
        } else {
            view.searchQrSuccess(asset)
        }
    }
}
