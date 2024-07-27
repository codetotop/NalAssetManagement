package com.example.nalassetmanagement.screen.inventory

import android.content.Context
import com.example.nalassetmanagement.extension.mapEntityToData
import com.example.nalassetmanagement.model.inventory.InventorySession
import com.example.nalassetmanagement.room.database.AppDatabase
import com.example.nalassetmanagement.room.entity.InventorySessionStatus
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class InventoryPresenter(private val view: InventoryContract.View): InventoryContract.Presenter {

    private val presenterJob = SupervisorJob()
    private val presenterScope = CoroutineScope(Dispatchers.IO + presenterJob)
    override fun fetchData() {

    }

    override fun executeInventory() {

    }

    override fun getInventorySessions() {
        presenterScope.launch {
            val listInventory = AppDatabase.getInstance((view as Context).applicationContext)
                .inventorySessionDao()
                .getAll()
                .mapEntityToData { it.toInventorySession() }

            withContext(Dispatchers.Main){
                val listInProgress = filterInventorySessionsInProgress(listInventory)
                val listEnd = filterInventorySessionsEnd(listInventory)
                view.getInventorySessionsSuccess(
                    listInventorySessionsInProgress = listInProgress,
                    listInventorySessionsEnd = listEnd
                )
            }
        }
    }
    private fun filterInventorySessionsInProgress(listInventory: List<InventorySession>) : List<InventorySession> {
        return listInventory.filter {
            InventorySessionStatus.IN_PROGRESS.value == it.status
        }.toList()
    }
    private fun filterInventorySessionsEnd(listInventory: List<InventorySession>) :List<InventorySession> {
        return listInventory.filter {
            InventorySessionStatus.END.value == it.status
        }.toList()
    }

    override fun getAssetsFromLocal() {
        presenterScope.launch {
            val listAssets = AppDatabase.getInstance((view as Context).applicationContext)
                .assetDao()
                .getAll()
                .mapEntityToData { it.toAsset() }
            withContext(Dispatchers.Main){
                view.getAssetsFromLocalSuccess(listAssets)
            }
        }
    }
}