package com.example.nalassetmanagement.screen.inventory.inventory_detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import com.example.nalassetmanagement.databinding.BottomSheetStatusInventoryBinding
import com.example.nalassetmanagement.model.inventory.AssetInventorySession
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class InventoryBottomSheet : BottomSheetDialogFragment() {
    lateinit var binding: BottomSheetStatusInventoryBinding
    private lateinit var itemAssetInventorySession: AssetInventorySession

    var onSuccessInventorySession: (AssetInventorySession) -> Unit = {}
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = BottomSheetStatusInventoryBinding.inflate(inflater, container, false)
        if (savedInstanceState != null) {
            if (isAdded) {
                dismiss()
            }
        }
        return binding.root
    }

    fun showDialog(
        fragmentManager: FragmentManager?,
        tag: String?,
        itemAsset: AssetInventorySession
    ) {
        fragmentManager?.let {
            show(fragmentManager, tag)
        }
        this.itemAssetInventorySession = itemAsset
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnCancel.setOnClickListener { this.dismiss() }
        binding.btnOke.setOnClickListener {
            addStatusDataAssetInventorySession()
        }
    }

    override fun onResume() {
        super.onResume()
        bindingView()
    }

    private fun addStatusDataAssetInventorySession() {
        val item = AssetInventorySession(
            statusId = getStatusId(),
            note = binding.textInputNote.text.toString(),
            idInventorySession = itemAssetInventorySession.idInventorySession,
            idAsset = itemAssetInventorySession.idAsset,
        )
        onSuccessInventorySession.invoke(item)
        dismiss()
    }

    private fun bindingView() {
        val isData = itemAssetInventorySession.statusId != null || itemAssetInventorySession.note?.isNotBlank() == true
        when (itemAssetInventorySession.statusId) {
            1 -> binding.good.isChecked = true
            2 -> binding.normal.isChecked = true
            3 -> binding.low.isChecked = true
        }
        binding.textInputNote.setText(itemAssetInventorySession.note)
        binding.btnOke.visibility = if (isData) View.GONE else View.VISIBLE
    }
    private fun getStatusId() : Int {
        return when (true) {
            binding.good.isChecked -> 1
            binding.normal.isChecked -> 2
            binding.low.isChecked -> 3
            else -> 1
        }
    }

    override fun dismiss() {
        super.dismiss()
        clearView()
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }

    private fun clearView() = binding.apply {
        textInputNote.setText("")
        good.isChecked = false
        normal.isChecked = false
        low.isChecked = false
    }
}