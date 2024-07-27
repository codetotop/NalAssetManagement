package com.example.nalassetmanagement.screen.inventory.inventory_detail

import com.example.nalassetmanagement.model.Asset
import com.example.nalassetmanagement.model.inventory.InventorySession

interface InventoryDetailContract {

    interface Presenter {
        fun fetchData()
        fun executeInventory()

        fun getAssetsFromLocal()
        fun getInventorySessionDetails(item : InventorySession)
    }

    interface View {
        fun loadData()
        fun getAssetsFromLocalSuccess(listAssetsChecked : MutableList<Asset>, listAssetsNotChecked : MutableList<Asset>)
        fun getAssetsFromLocalError()
    }
}