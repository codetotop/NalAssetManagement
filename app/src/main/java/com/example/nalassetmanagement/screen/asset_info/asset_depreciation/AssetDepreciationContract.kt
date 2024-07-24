package com.example.nalassetmanagement.screen.asset_info.asset_depreciation

interface AssetDepreciationContract {

    interface Presenter {
        fun fetchAssetList()
    }

    interface View {
        fun loadAssetList()
    }
}
