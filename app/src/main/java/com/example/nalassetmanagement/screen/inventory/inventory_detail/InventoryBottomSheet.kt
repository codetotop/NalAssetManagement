package com.example.nalassetmanagement.screen.inventory.inventory_detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.nalassetmanagement.R
import com.example.nalassetmanagement.base.BaseBottomSheet
import com.example.nalassetmanagement.databinding.BottomSheetStatusInventoryBinding

class InventoryBottomSheet : BaseBottomSheet<BottomSheetStatusInventoryBinding>() {
    override fun getViewBinding(inflater: LayoutInflater, container: ViewGroup?): BottomSheetStatusInventoryBinding {
        return BottomSheetStatusInventoryBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}