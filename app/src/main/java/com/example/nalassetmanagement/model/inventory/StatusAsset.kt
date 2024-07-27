package com.example.nalassetmanagement.model.inventory

data class StatusAsset (
    val idAsset: Int,
    val idInventorySession: Int,
    val status: String? = null,
    val note : String? = null
)