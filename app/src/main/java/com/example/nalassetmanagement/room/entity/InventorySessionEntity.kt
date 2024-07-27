package com.example.nalassetmanagement.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.nalassetmanagement.model.inventory.InventorySession
import com.example.nalassetmanagement.model.KeyValue

@Entity(tableName = "inventory_session")
data class InventorySessionEntity (
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val locationInventory: String? = null,
    val startDate: String? = null,
    val endDate: String? = null,

    val status: String? = null,
) {
    fun toInventorySession(): InventorySession {
        return InventorySession(
            id = id,
            locationInventory = locationInventory,
            startDate = startDate,
            endDate = endDate,
            status = status,
            user = KeyValue(name = "admin")
        )
    }
}

enum class InventorySessionStatus(val value: String) {
    IN_PROGRESS("open"), END("closed");
}
enum class LocationInventory(value: String) {
    NAL_KOSMO("Nal_Kosmo"),
    NAL_THIEN_HIEN("Nal_Thien_Hien")
}