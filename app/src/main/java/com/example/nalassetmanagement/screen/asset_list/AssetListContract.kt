package com.example.nalassetmanagement.screen.asset_list

import com.example.nalassetmanagement.model.Data
import com.example.nalassetmanagement.model.server.Asset
import com.example.nalassetmanagement.model.server.AssetList

interface AssetListContract {

    interface Presenter {
        fun login(displayName: String, password: String)

        fun fetchAssetList(page: Int)

        fun searchQr(qrText: String, assetListResponses: List<Asset>)
    }

    interface View {
        fun loginSuccess(data: Data)
        fun loginFailure()

        fun fetchAssetListSuccess(data: AssetList?)
        fun fetchAssetListFailure()

        fun searchQrSuccess(asset: Asset)
        fun searchQrFailure()
    }
}
