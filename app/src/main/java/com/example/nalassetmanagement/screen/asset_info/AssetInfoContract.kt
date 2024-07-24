package com.example.nalassetmanagement.screen.asset_info

interface AssetInfoContract {

    interface Presenter {
        fun fetchData()
        fun executeInventory()
    }

    interface View {
        fun loadData()
    }
}
