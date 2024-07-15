package com.example.nalassetmanagement.screen.asset_list

interface AssetListContract {

    interface Presenter {
        fun fetchAssetList()
    }

    interface View {
        fun loadAssetList()
    }
}