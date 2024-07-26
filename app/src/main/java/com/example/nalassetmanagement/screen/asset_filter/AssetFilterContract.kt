package com.example.nalassetmanagement.screen.asset_filter

import com.example.nalassetmanagement.model.FilterList
import com.example.nalassetmanagement.model.FilterListResponse

interface AssetFilterContract {

    interface Presenter {
        fun fetchData()
    }

    interface View {
        fun fetchFilterListSuccess(filterList: FilterList?)
        fun fetchFilterListFailure()
    }
}
