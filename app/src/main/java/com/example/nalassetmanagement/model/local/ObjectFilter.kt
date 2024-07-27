package com.example.nalassetmanagement.model.local

import java.io.Serializable

data class ObjectFilter(val id: Int? = 0, val name: String? = "", var isSelected: Boolean? = false): Serializable
