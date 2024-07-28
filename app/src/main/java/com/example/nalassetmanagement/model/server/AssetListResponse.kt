package com.example.nalassetmanagement.model.server

import com.example.nalassetmanagement.room.entity.AssetEntity
import com.google.gson.annotations.SerializedName
import java.io.Serializable
import kotlin.collections.List

class AssetListResponse : BaseResponse<AssetList>()

data class AssetList(
    @SerializedName("current_page")
    val currentPage: Int? = null,
    @SerializedName("data")
    val data: List<Asset>? = null,
    @SerializedName("first_page_url")
    val firstPageUrl: String? = null,
    @SerializedName("from")
    val from: Int? = null,
    @SerializedName("last_page")
    val lastPage: Int? = null,
    @SerializedName("last_page_url")
    val lastPageUrl: String? = null,
    @SerializedName("next_page_url")
    val nextPageUrl: String? = null,
    @SerializedName("path")
    val path: String? = null,
    @SerializedName("per_page")
    val perPage: Int? = null,
    @SerializedName("prev_page_url")
    val prevPageUrl: String? = null,
    @SerializedName("to")
    val to: Int? = null,
    @SerializedName("total")
    val total: Int? = null,
): Serializable

data class Asset(
    @SerializedName("id")
    val id: Int? = null,
    @SerializedName("name")
    val name: String? = null,
    @SerializedName("qr_code")
    val qrCode: String? = null,
    @SerializedName("address_id")
    val addressId: Int? = null,
    @SerializedName("status_id")
    val statusId: Int? = null,
    @SerializedName("category_id")
    val categoryId: Int? = null,
    @SerializedName("producer_id")
    val producerId: Int? = null,
    @SerializedName("model_id")
    val modelId: Int? = null,
    @SerializedName("created_at")
    val createdAt: String? = null,
    @SerializedName("updated_at")
    val updatedAt: String? = null,
    @SerializedName("deleted_at")
    val deletedAt: String? = null,
    @SerializedName("user_id")
    val userId: Int? = null,
    @SerializedName("category")
    val category: KeyValue? = null,
    @SerializedName("address")
    val address: KeyValue? = null,
    @SerializedName("models")
    val models: KeyValue? = null,
    @SerializedName("producer")
    val producer: KeyValue? = null,
    @SerializedName("status")
    val status: KeyValue? = null,
    @SerializedName("user")
    val user: User? = null,
): Serializable , Cloneable{
    fun toAssetEntity(): AssetEntity {
        return AssetEntity(
            name = name,
            qrCode = qrCode,
            addressId = addressId,
            categoryId = categoryId,
            producerId = producerId,
            modelId = modelId,
            createdAt = createdAt,
            updatedAt = updatedAt,
            deletedAt = deletedAt,
            userId = userId,
            category = category?.name,
            address = address?.name,
            models = models?.name,
            producer = producer?.name,
            user = user?.userName
        )
    }
}

data class KeyValue(
    @SerializedName("id")
    val id: Int? = null,
    @SerializedName("name")
    val name: String? = null
): Serializable

data class User(
    @SerializedName("id")
    val id: Int? = null,
    @SerializedName("username")
    val userName: String? = null
): Serializable
