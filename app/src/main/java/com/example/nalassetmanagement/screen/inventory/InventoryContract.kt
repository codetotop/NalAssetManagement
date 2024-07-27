package com.example.nalassetmanagement.screen.inventory

import com.example.nalassetmanagement.model.Asset
import com.example.nalassetmanagement.model.inventory.InventorySession

interface InventoryContract {

    interface Presenter {
        fun fetchData()
        fun executeInventory()

        fun getInventorySessions()

        fun getAssetsFromLocal()
    }

    interface View {
        fun loadData()

        fun getInventorySessionsSuccess(listInventorySessionsEnd: List<InventorySession>, listInventorySessionsInProgress: List<InventorySession>)
        fun getInventorySessionsError()
        fun getAssetsFromLocalSuccess(listAssets: List<Asset>)
        fun getAssetsFromLocalError()
    }
}