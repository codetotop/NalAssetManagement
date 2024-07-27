package com.example.nalassetmanagement.screen.inventory.inventory_detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import com.example.nalassetmanagement.R
import com.example.nalassetmanagement.base.BaseBottomSheet
import com.example.nalassetmanagement.databinding.BottomSheetStatusInventoryBinding
import com.example.nalassetmanagement.view.custom.BottomSheet
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class InventoryBottomSheet : BottomSheetDialogFragment() {
    lateinit var binding : BottomSheetStatusInventoryBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = BottomSheetStatusInventoryBinding.inflate(inflater,container,false)
        if (savedInstanceState != null) {
            if (isAdded) {
                dismiss()
            }
        }
        return binding.root
    }
    fun showDialog(
        fragmentManager: FragmentManager?,
        tag: String?
    ) {
        fragmentManager?.let {
            show(fragmentManager,tag)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnCancel.setOnClickListener{this.dismiss()}

    }
    interface OnClickBottomSheet{
        fun onClickSuccess(status : String)
    }
}