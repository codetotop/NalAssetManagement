package com.example.nalassetmanagement.screen.inventory.inventory_detail

import android.app.Activity
import com.example.nalassetmanagement.extension.mapDataToEntity
import com.example.nalassetmanagement.extension.mapEntityToData
import com.example.nalassetmanagement.model.inventory.AssetInventorySession
import com.example.nalassetmanagement.model.inventory.InventorySession
import com.example.nalassetmanagement.model.server.Asset
import com.example.nalassetmanagement.model.server.KeyValue
import com.example.nalassetmanagement.room.database.AppDatabase
import com.example.nalassetmanagement.room.entity.AssetEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Locale

class InventoryDetailPresenter(private val view: InventoryDetailContract.View) :
    InventoryDetailContract.Presenter {

    private val presenterJob = SupervisorJob()
    private val presenterScope = CoroutineScope(Dispatchers.IO + presenterJob)

    private var listAssetsInventorySession = mutableListOf<AssetInventorySession>()

    override fun fetchData() {

    }

    override fun executeInventory() {

    }

    override fun getAllAsset() {
        presenterScope.launch {
            val listAssets = AppDatabase.getInstance((view as Activity).applicationContext)
                .assetDao().getAll().mapEntityToData {
                    it.toAsset()
                }.toMutableList()
            withContext(Dispatchers.Main) {
                view.getAllAssetSuccess(listAssets)
            }
        }
    }

    override fun getInventorySessionDetails(item: InventorySession) {
        if (item.id == null)
            return
        presenterScope.launch {
            val list = AppDatabase.getInstance((view as Activity).applicationContext)
                    .assetInventorySessionDao()
                    .getByIdInventorySession(item.id)
                    .mapDataToEntity {
                        it.toAssetInventorySession()
                    }
                    .toMutableList()
            withContext(Dispatchers.Main) {
                listAssetsInventorySession = list
            }
            getAssetsFromLocal()
        }
    }

    override fun searchQrCodeFormLocal(qrCode: String) {
        presenterScope.launch {
            val item = AppDatabase.getInstance((view as Activity).applicationContext)
                .assetDao()
                .getItemByQrCode(qrCode)
                ?.toAsset()
            if (item == null) {
                presenterScope.cancel()
                return@launch
            }
            val listAssetsChecked = filterAssetsChecked(mutableListOf(item), listAssetsInventorySession)
            val listAssetsNotChecked = filterAssetsNotChecked(mutableListOf(item), listAssetsInventorySession)
            withContext(Dispatchers.Main) {
                view.getAssetsFromLocalSuccess(listAssetsChecked,listAssetsNotChecked)
            }
        }
    }

    override fun searchNameAssetOrQrCodeOrNameUserUse(list: List<Asset>, text: String) {
        val temp: MutableList<Asset> = ArrayList()
        for (item in list) {
            val lDapName = item.user?.userName?.trim()?.lowercase(Locale.ROOT)
            val assetName = item.name?.trim()?.lowercase(Locale.ROOT)
            val qrCode = item.qrCode?.trim()?.lowercase(Locale.ROOT)
            val textLowerCase = text.trim().lowercase(Locale.ROOT)
            if (lDapName?.contains(textLowerCase) == true
                || assetName?.contains(textLowerCase) == true
                || qrCode?.contains(textLowerCase) == true
            ) {
                temp.add(item)
            }
        }

        val listAssetsChecked = filterAssetsChecked(temp, listAssetsInventorySession)
        val listAssetsNotChecked = filterAssetsNotChecked(temp, listAssetsInventorySession)

        view.getAssetsFromLocalSuccess(listAssetsChecked,listAssetsNotChecked)
    }

    override fun addAssetAlreadyInInventory(item: AssetInventorySession, addItemSuccess: () -> Unit) {
        presenterScope.launch {
            AppDatabase.getInstance((view as Activity).applicationContext)
                .assetInventorySessionDao()
                .insert(item.toAssetOfInventorySessionEntity())
            withContext(Dispatchers.Main) {
                addItemSuccess.invoke()
            }
        }
    }

    override fun findInListAssetInventorySession(idAsset: Int, idInventorySession: Int): AssetInventorySession {
        val item = listAssetsInventorySession.find { it.idAsset == idAsset }
        return item ?: AssetInventorySession(
            idAsset = idAsset,
            idInventorySession = idInventorySession)
    }

    override fun getAssetsFromLocal() {
        presenterScope.launch {
            val listAssets = AppDatabase.getInstance((view as Activity).applicationContext)
                .assetDao()
                .getAll()
                .mapDataToEntity {
                    it.toAsset()
                }
                .toMutableList()

            val listAssetsChecked = filterAssetsChecked(listAssets, listAssetsInventorySession)
            val listAssetsNotChecked = filterAssetsNotChecked(listAssets, listAssetsInventorySession)

            withContext(Dispatchers.Main) {
                view.getAssetsFromLocalSuccess(listAssetsChecked, listAssetsNotChecked)
            }
        }
    }
    private fun filterAssetsChecked(
        listAssets: MutableList<Asset>,
        listAssetsInventorySession: MutableList<AssetInventorySession>
    ): MutableList<Asset> {
        val listIDChecked = listAssetsInventorySession.map { it.idAsset }.toSet()

        val listAssetsAlreadyFillStatus = fillStatusToAssetInventorySession(listAssets, listAssetsInventorySession)
        return listAssetsAlreadyFillStatus.filter { asset -> asset.id in listIDChecked }
            .toMutableList()
    }

    private fun filterAssetsNotChecked(
        listAssets: MutableList<Asset>,
        listAssetsInventorySession: MutableList<AssetInventorySession>
    ): MutableList<Asset> {
        val listIDChecked = listAssetsInventorySession.map { it.idAsset }.toSet()

        return listAssets.filter { asset -> asset.id !in listIDChecked }
            .toMutableList()
    }
    private fun fillStatusToAssetInventorySession(listAssets: MutableList<Asset>, listAssetsInventorySession: MutableList<AssetInventorySession>) : MutableList<Asset>{
        val updatedList = listAssets.map { asset ->
            var assetAlreadyFill = Asset()
            for (item in listAssetsInventorySession) {
                if (asset.id == item.idAsset) {
                    assetAlreadyFill = asset.copy(status = KeyValue(id = item.statusId, name = AssetEntity.getStatus(item.statusId)))
                    break
                }
            }
            assetAlreadyFill
        }.toMutableList()
        return updatedList
    }
}