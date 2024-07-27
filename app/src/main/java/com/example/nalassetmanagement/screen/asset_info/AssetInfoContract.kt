package com.example.nalassetmanagement.screen.asset_info

import com.example.nalassetmanagement.model.server.Asset

interface AssetInfoContract {

    interface Presenter {
        fun fetchAssetDetail(id: Int)
        fun fetchFilterList()
    }

    interface View {
        fun fetchAssetDetailSuccess(data: Asset?)

        fun fetchAssetDetailFailure()
    }
}
