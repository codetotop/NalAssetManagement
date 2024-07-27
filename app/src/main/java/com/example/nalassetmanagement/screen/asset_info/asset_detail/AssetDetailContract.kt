package com.example.nalassetmanagement.screen.asset_info.asset_detail

import com.example.nalassetmanagement.model.server.FilterList

interface AssetDetailContract {

    interface Presenter {
        fun fetchFilterList()
        fun updateStatusAsset(id: Int, type: String, key: String, value: String)
    }

    interface View {
        fun fetchFilterListSuccess(data: FilterList?)
        fun fetchFilterListFailure()

        fun updateStatusSuccess()
        fun updateStatusFailure()
    }
}
