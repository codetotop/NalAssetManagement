package com.example.nalassetmanagement.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.nalassetmanagement.room.entity.AssetInventorySessionEntity

@Dao
interface AssetInventorySessionDao {
    @Insert
    fun insert(vararg item: AssetInventorySessionEntity)
    @Query("SELECT * FROM asset_inventory_session WHERE idAsset = :idAsset AND idInventorySession = :idInventorySession")
    fun get(idAsset: Int, idInventorySession: Int): List<AssetInventorySessionEntity?>
}