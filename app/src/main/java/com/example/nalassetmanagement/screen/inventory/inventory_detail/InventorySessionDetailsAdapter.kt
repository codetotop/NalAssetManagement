package com.example.nalassetmanagement.screen.inventory.inventory_detail

import androidx.recyclerview.widget.RecyclerView

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.nalassetmanagement.R
import com.example.nalassetmanagement.databinding.ItemAssetBinding
import com.example.nalassetmanagement.model.server.Asset
import com.example.nalassetmanagement.model.server.KeyValue
import com.example.nalassetmanagement.view.extension.loadWithPicasso

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
                binding.tvUser.text = item.user?.userName ?: "Đang trống"
                binding.tvAddress.setText(item.address?.name)
                binding.tvStatus.text = item.status?.name?: "Tốt"
                binding.tvStatus.let {
                    binding.tvStatus.setBackgroundResource(backgroundStatus(status = item.status))
                }
                binding.imgAssetAvatar.loadWithPicasso("", item.modelId)

                if (item.status == null) {
                    binding.tvStatus.visibility = View.GONE
                }
                else {
                    binding.tvStatus.visibility = View.VISIBLE
                }
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
    fun backgroundStatus(status: KeyValue?): Int {
        when (status?.id) {
            1 -> {
                return R.drawable.bg_tv_status_green_light
            }

            2 -> {
                return R.drawable.bg_tv_status_yellow
            }

            3 -> {
                return R.drawable.bg_tv_status_red
            }

            4, 5 -> {
                return R.drawable.bg_tv_status_green_dark
            }

            else -> {
                return R.drawable.bg_tv_status_gray
            }
        }
    }
}
