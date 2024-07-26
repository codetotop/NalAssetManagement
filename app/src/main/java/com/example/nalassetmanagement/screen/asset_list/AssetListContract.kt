package com.example.nalassetmanagement.screen.asset_list

import com.example.nalassetmanagement.model.Data
import com.example.nalassetmanagement.model.AssetList

interface AssetListContract {

    interface Presenter {
        fun login(displayName: String, password: String)

        fun fetchAssetList(page: Int)
    }

    interface View {
        fun loginSuccess(data: Data)
        fun loginFailure()

        fun fetchAssetListSuccess(data: AssetList?)
        fun fetchAssetListFailure()
    }
}
