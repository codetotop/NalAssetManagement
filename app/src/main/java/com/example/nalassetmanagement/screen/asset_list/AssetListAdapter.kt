package com.example.nalassetmanagement.screen.asset_list

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.nalassetmanagement.databinding.ItemAssetBinding
import com.example.nalassetmanagement.model.server.Asset
import com.example.nalassetmanagement.view.extension.loadWithPicasso

class AssetListAdapter(private var list: List<Asset>, private var listener: OnClickListener) :
    RecyclerView.Adapter<AssetListAdapter.AssetListViewHolder>() {

    class AssetListViewHolder(val binding: ItemAssetBinding) :
        RecyclerView.ViewHolder(binding.root)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AssetListViewHolder {
        val binding = ItemAssetBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return AssetListViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun replaceList(list: List<Asset>) {
        this.list = list
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: AssetListViewHolder, itemPosition: Int) {
        with(holder) {
            with(list[itemPosition]) {
                binding.imgAssetAvatar.loadWithPicasso(qrCode)
                binding.tvAssetName.text = name
                binding.tvUser.text = user?.userName ?: ""
                binding.tvAddress.text = address?.name ?: "LA"
                binding.tvStatus.text = status?.name?: "Đã bán"

                binding.root.setOnClickListener {
                    listener.onItemClick(itemPosition)
                }
            }
        }
    }

    interface OnClickListener {
        fun onItemClick(position: Int)
    }
}

