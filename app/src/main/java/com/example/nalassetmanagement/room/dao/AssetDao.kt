package com.example.nalassetmanagement.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.nalassetmanagement.room.entity.AssetEntity

@Dao
interface AssetDao {
    @Insert
    fun insert(vararg assetEntities: AssetEntity)
    @Query("SELECT * FROM asset")
    fun getAll(): List<AssetEntity>
    @Query("SELECT * FROM asset WHERE id = :id")
    fun get(id: Int): AssetEntity
    @Query("SELECT * FROM asset WHERE qrCode == :qrCode")
    fun getItemByQrCode(qrCode: String): AssetEntity?
}