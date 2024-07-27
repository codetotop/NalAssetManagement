package com.example.nalassetmanagement.screen.inventory.inventory_detail

import android.app.Activity
import com.example.nalassetmanagement.extension.mapDataToEntity
import com.example.nalassetmanagement.model.Asset
import com.example.nalassetmanagement.model.inventory.InventorySession
import com.example.nalassetmanagement.room.database.AppDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class InventoryDetailPresenter(private val view: InventoryDetailContract.View) :
    InventoryDetailContract.Presenter {

    private val presenterJob = SupervisorJob()
    private val presenterScope = CoroutineScope(Dispatchers.IO + presenterJob)
    private var listAssetsInventorySession = mutableListOf<InventorySession>()
    private var listAssets = mutableListOf<Asset>()
    override fun fetchData() {

    }

    override fun executeInventory() {

    }

    override fun getInventorySessionDetails(item: InventorySession) {
        presenterScope.launch {
            val list = AppDatabase.getInstance((view as Activity).applicationContext)
                    .inventorySessionDao()
                    .getAll()
                    .mapDataToEntity {
                        it.toInventorySession()
                    }
                    .toMutableList()
            withContext(Dispatchers.Main) {
                listAssetsInventorySession = list
            }
            getAssetsFromLocal()
        }
    }
    override fun getAssetsFromLocal() {
        presenterScope.launch {
            val list = AppDatabase.getInstance((view as Activity).applicationContext)
                .assetDao()
                .getAll()
                .mapDataToEntity {
                    it.toAsset()
                }
                .toMutableList()

            withContext(Dispatchers.Main) {
                listAssets = list
                val listAssetsChecked = filterAssetsChecked(listAssets, listAssetsInventorySession)
                val listAssetsNotChecked = filterAssetsNotChecked(listAssets, listAssetsInventorySession)
                view.getAssetsFromLocalSuccess(listAssetsChecked, listAssetsNotChecked)
            }
        }

    }
    private fun filterAssetsChecked(
        listAssets: MutableList<Asset>,
        listAssetsInventorySession: MutableList<InventorySession>
    ): MutableList<Asset> {
        val listIDChecked = listAssetsInventorySession.mapNotNull { it.id }.toSet()
        return listAssets.filter { asset -> asset.id?.toIntOrNull() in listIDChecked }
            .toMutableList()
    }

    private fun filterAssetsNotChecked(
        listAssets: MutableList<Asset>,
        listAssetsInventorySession: MutableList<InventorySession>
    ): MutableList<Asset> {
        val listIDChecked = listAssetsInventorySession.mapNotNull { it.id }.toSet()
        return listAssets.filter { asset -> asset.id?.toIntOrNull() !in listIDChecked }
            .toMutableList()
    }
}