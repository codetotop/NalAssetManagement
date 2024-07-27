package com.example.nalassetmanagement.screen.inventory.inventory_detail

import androidx.recyclerview.widget.RecyclerView

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.nalassetmanagement.databinding.ItemAssetBinding
import com.example.nalassetmanagement.databinding.ItemInventorySessionBinding
import com.example.nalassetmanagement.model.Asset
import com.example.nalassetmanagement.model.inventory.InventorySession

class InventorySessionDetailsAdapter(val onClickItemAsset : OnClickItemAsset) :
    ListAdapter<Asset, InventorySessionDetailsAdapter.ViewHolder>(InventorySessionDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemAssetBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
        holder.itemView.setOnClickListener {
            onClickItemAsset.onClickItemAsset(item)
        }
    }

    inner class ViewHolder(private val binding: ItemAssetBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Asset) {
            binding.apply {
                binding.tvAssetName.text = item.name
                binding.tvUser.text = item.user?.name ?: "Đang trống"
                binding.tvAddress.setText(item.user?.name)
                binding.tvStatus.text = item.status?.name?: "Đã bán"
            }
        }
    }

    class InventorySessionDiffCallback : DiffUtil.ItemCallback<Asset>() {
        override fun areItemsTheSame(oldItem: Asset, newItem: Asset): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Asset, newItem: Asset): Boolean {
            return oldItem == newItem
        }
    }

    interface OnClickItemAsset {
        fun onClickItemAsset(item: Asset)
    }
}
