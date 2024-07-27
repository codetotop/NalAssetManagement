package com.example.nalassetmanagement.screen.inventory


import com.example.nalassetmanagement.model.inventory.InventorySession
import com.example.nalassetmanagement.model.server.Asset

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