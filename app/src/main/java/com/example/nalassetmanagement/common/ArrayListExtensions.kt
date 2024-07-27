package com.example.nalassetmanagement.common

import com.example.nalassetmanagement.model.local.ObjectFilter

fun ArrayList<ObjectFilter>.selectedPosition(): Int {
    for (index in indices) {
        if (get(index).isSelected == true) return index
    }
    return 0
}

fun ArrayList<ObjectFilter>.singleSelected(position: Int) {
    for (index in indices) {
        get(index).isSelected = index == position
    }
}
