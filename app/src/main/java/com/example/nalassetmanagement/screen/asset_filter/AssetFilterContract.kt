package com.example.nalassetmanagement.screen.asset_filter

import com.example.nalassetmanagement.model.server.FilterList

interface AssetFilterContract {

    interface Presenter {
        fun fetchFilterList()
    }

    interface View {
        fun fetchFilterListSuccess(filterList: FilterList?)
        fun fetchFilterListFailure()
    }
}
