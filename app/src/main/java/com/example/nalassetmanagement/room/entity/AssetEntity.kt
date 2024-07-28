package com.example.nalassetmanagement.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.nalassetmanagement.model.server.Asset
import com.example.nalassetmanagement.model.server.KeyValue
import com.example.nalassetmanagement.model.server.User

@Entity(tableName = "asset")
data class AssetEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,

    val name: String? = null,

    val qrCode: String? = null,

    val addressId: Int? = null,

    val statusId: Int? = null,

    val categoryId: Int? = null,

    val producerId: Int? = null,

    val modelId: Int? = null,

    val createdAt: String? = null,

    val updatedAt: String? = null,

    val deletedAt: String? = null,

    val userId: Int? = null,

    val category: String? = null,

    val address: String? = null,

    val models: String? = null,

    val producer: String? = null,

    val status: String? = null,

    val user: String? = null,
) {
    fun toAsset(): Asset {
        return Asset(
            id = id,
            name = name,
            qrCode = qrCode,
            addressId = addressId,
            statusId = statusId,
            categoryId = categoryId,
            producerId = producerId,
            modelId = modelId,
            createdAt = createdAt,
            updatedAt = updatedAt,
            deletedAt = deletedAt,
            userId = userId,
            category = KeyValue(name = category),
            address = KeyValue(name = address),
            models = KeyValue(name = models),
            producer = KeyValue(name = producer),
            status = KeyValue(name = getStatus(statusId), id = statusId),
            user = User(userName = user),
        )
    }
    private  fun getStatus(status: Int?) :String {
        return when(true) {
            (status == 1 ) -> "Tốt"
            (status == 2) -> "Bình thường"
            (status == 3) -> "Tệ"
            else -> "Bình thường"
        }
    }
}
