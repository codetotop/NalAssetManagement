package com.example.nalassetmanagement.screen.asset_info.asset_location

interface AssetLocationContract {

    interface Presenter {
        fun fetchData()
    }

    interface View {
        fun loadData()
        fun loadError()
    }
}
