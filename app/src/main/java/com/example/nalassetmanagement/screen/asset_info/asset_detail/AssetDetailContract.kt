package com.example.nalassetmanagement.screen.asset_info.asset_detail

interface AssetDetailContract {

    interface Presenter {
        fun fetchData()
        fun executeInventory()
    }

    interface View {
        fun loadData()
    }
}
