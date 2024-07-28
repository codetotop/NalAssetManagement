package com.example.nalassetmanagement.room.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import com.example.nalassetmanagement.model.inventory.AssetInventorySession


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
    val statusId : Int?,
    val note : String? = null,
) {
    fun toAssetInventorySession() : AssetInventorySession {
        return AssetInventorySession(
            idInventorySession = idInventorySession,
            idAsset = idAsset,
            status = status,
            statusId = statusId,
            note = note
        )
    }
}
enum class StatusAssetInventorySession(value : String) {
    GOOD("Tốt"),
    NORMAL("Bình thường"),
    BAD("Tệ"),
}