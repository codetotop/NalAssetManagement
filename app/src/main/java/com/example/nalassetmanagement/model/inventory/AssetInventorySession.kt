package com.example.nalassetmanagement.model.inventory

import com.example.nalassetmanagement.room.entity.AssetInventorySessionEntity

data class AssetInventorySession (
    val idAsset: Int,
    val idInventorySession: Int,
    val statusId: Int? = null,
    val status: String? = null,
    val note : String? = null
) {
    fun toAssetOfInventorySessionEntity(): AssetInventorySessionEntity {
        return AssetInventorySessionEntity(
            idAsset = idAsset,
            idInventorySession = idInventorySession,
            statusId = statusId,
            status = status,
            note = note
        )
    }
}