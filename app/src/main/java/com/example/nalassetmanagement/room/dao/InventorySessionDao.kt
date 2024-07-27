package com.example.nalassetmanagement.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.nalassetmanagement.room.entity.InventorySessionEntity

@Dao
interface InventorySessionDao {
    @Query("SELECT * FROM inventory_session")
    fun getAll(): List<InventorySessionEntity>
    @Query("SELECT * FROM inventory_session WHERE id = :id")
    fun getInventorySessionById(id: Int): InventorySessionEntity?
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vararg inventorySession: InventorySessionEntity)

}