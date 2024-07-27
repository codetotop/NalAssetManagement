package com.example.nalassetmanagement.room.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import com.example.nalassetmanagement.model.inventory.StatusAsset


@Entity(
    tableName = "asset_inventory_session",
    primaryKeys = ["idAsset", "idInventorySession"],
    foreignKeys = [
        ForeignKey(entity = AssetEntity::class, parentColumns = ["id"], childColumns = ["idAsset"]),
        ForeignKey(entity = InventorySessionEntity::class, parentColumns = ["id"], childColumns = ["idInventorySession"])
    ],
    indices = [Index(value = ["idAsset"]), Index(value = ["idInventorySession"])]
)
data class AssetInventorySessionEntity (
    val idAsset: Int,
    val idInventorySession: Int,
    val status : String? = null,
    val note : String? = null,
) {
    fun toStatusAsset() : StatusAsset {
        return StatusAsset(
            idInventorySession = idInventorySession,
            idAsset = idAsset,
            status = status,
            note = note
        )
    }
}
enum class StatusAssetInventorySession(value : String) {
    GOOD("Tốt"),
    NORMAL("Bình thường"),
    BAD("Kém"),
    LIQUIDATE("Xin thanh lý")
}