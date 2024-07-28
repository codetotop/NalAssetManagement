package com.example.nalassetmanagement.screen.inventory.inventory_detail

import com.example.nalassetmanagement.model.inventory.AssetInventorySession
import com.example.nalassetmanagement.model.inventory.InventorySession
import com.example.nalassetmanagement.model.server.Asset

interface InventoryDetailContract {

    interface Presenter {
        fun fetchData()
        fun executeInventory()

        fun getAllAsset()

        fun getAssetsFromLocal()
        fun getInventorySessionDetails(item : InventorySession)
        fun findInListAssetInventorySession(idAsset: Int, idInventorySession: Int) : AssetInventorySession

        fun searchQrCodeFormLocal(qrCode: String)


        fun searchNameAssetOrQrCodeOrNameUserUse(list: List<Asset>, text: String)

        fun addAssetAlreadyInInventory(item : AssetInventorySession,addItemSuccess: () -> Unit)
    }

    interface View {
        fun loadData()

        fun getAllAssetSuccess(listAsset : MutableList<Asset>)
        fun getAssetsFromLocalSuccess(listAssetsChecked : MutableList<Asset>, listAssetsNotChecked : MutableList<Asset>)
        fun getAssetsFromLocalError()

    }
}