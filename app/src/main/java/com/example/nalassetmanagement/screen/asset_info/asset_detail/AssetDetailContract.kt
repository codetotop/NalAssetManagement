package com.example.nalassetmanagement.screen.asset_info.asset_detail

import com.example.nalassetmanagement.model.server.Asset
import com.example.nalassetmanagement.model.server.FilterList

interface AssetDetailContract {

    interface Presenter {
        fun fetchAssetDetail(id: Int)
        fun fetchFilterList()
        fun updateStatusAsset(id: Int, type: String, key: String, value: String)
        fun updateQrCodeAsset(id: Int, type: String, value: String)
    }

    interface View {
        fun fetchAssetDetailSuccess(data: Asset?)
        fun fetchAssetDetailFailure()

        fun fetchFilterListSuccess(data: FilterList?)
        fun fetchFilterListFailure()

        fun updateStatusSuccess()
        fun updateStatusFailure()

        fun updateQrCodeSuccess(strQrCode: String)
        fun updateQrCodeFailure()
    }
}
