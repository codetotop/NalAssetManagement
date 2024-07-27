package com.example.nalassetmanagement.model.inventory

import com.example.nalassetmanagement.model.KeyValue
import com.example.nalassetmanagement.room.entity.InventorySessionEntity
import java.io.Serializable

data class InventorySession(
    val id: Int? = null,
    val locationInventory: String? = null,
    val startDate: String? = null,
    val endDate: String? = null,
    val user: KeyValue? = null,
    val status: String? = null,
) : Serializable {
    fun toInventorySessionEntity(): InventorySessionEntity {
        return InventorySessionEntity(
            locationInventory = locationInventory,
            startDate = startDate,
            endDate = endDate,
            status = status,
        )
    }
}