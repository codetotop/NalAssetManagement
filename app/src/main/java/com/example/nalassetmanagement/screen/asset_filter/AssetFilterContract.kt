package com.example.nalassetmanagement.screen.asset_filter

interface AssetFilterContract {

    interface Presenter {
        fun fetchData()
    }

    interface View {
        fun loadData()
    }
}
