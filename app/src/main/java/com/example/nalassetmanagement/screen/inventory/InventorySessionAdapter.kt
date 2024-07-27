package com.example.nalassetmanagement.screen.inventory

import androidx.recyclerview.widget.RecyclerView

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.nalassetmanagement.databinding.ItemInventorySessionBinding
import com.example.nalassetmanagement.model.inventory.InventorySession

class InventorySessionAdapter(val onClickItemInventorySession: OnClickItemInventorySession) :
    ListAdapter<InventorySession, InventorySessionAdapter.ViewHolder>(InventorySessionDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemInventorySessionBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
        holder.itemView.setOnClickListener {
            onClickItemInventorySession.onClickItemInventorySession(item)
        }
    }

    inner class ViewHolder(private val binding: ItemInventorySessionBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(inventorySession: InventorySession) {
            binding.apply {
                phienKiemKe.text = inventorySession.locationInventory
                startDate.text = inventorySession.startDate
                endDate.text = inventorySession.endDate
            }
        }
    }

    class InventorySessionDiffCallback : DiffUtil.ItemCallback<InventorySession>() {
        override fun areItemsTheSame(oldItem: InventorySession, newItem: InventorySession): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: InventorySession, newItem: InventorySession): Boolean {
            return oldItem == newItem
        }
    }

    interface OnClickItemInventorySession {
        fun onClickItemInventorySession(inventorySession: InventorySession)
    }
}
