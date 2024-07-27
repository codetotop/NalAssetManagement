package com.example.nalassetmanagement.screen.asset_list

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.nalassetmanagement.R
import com.example.nalassetmanagement.databinding.ItemAssetBinding
import com.example.nalassetmanagement.model.server.Asset
import com.example.nalassetmanagement.model.server.KeyValue
import com.example.nalassetmanagement.view.extension.loadWithPicasso
import java.util.Locale

class AssetListAdapter(var list: List<Asset>, private var listener: OnClickListener) :
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
                binding.imgAssetAvatar.loadWithPicasso("", categoryId)
                binding.tvAssetName.text = name
                binding.tvUser.text = user?.userName ?: ""
                binding.tvAddress.text = address?.name ?: "Kosmo"
                binding.tvStatus.text = status?.name ?: "Quá tốt"
                status?.let {
                    binding.tvStatus.setBackgroundResource(backgroundStatus(it))
                }

                binding.root.setOnClickListener {
                    listener.onItemClick(itemPosition)
                }
            }
        }
    }

    private fun backgroundStatus(status: KeyValue): Int {
        when (status.id) {
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

    fun filter(list: List<Asset>, text: String) {
        val temp: MutableList<Asset> = ArrayList()
        for (d in list) {
            val lDapName = d.user?.userName?.trim()?.lowercase(Locale.ROOT)
            val assetName = d.name?.trim()?.lowercase(Locale.ROOT)
            val textLowerCase = text.trim().lowercase(Locale.ROOT)
            if (lDapName?.contains(textLowerCase) == true || assetName?.contains(textLowerCase) == true) {
                temp.add(d)
            }
        }

        replaceList(temp)
    }

    interface OnClickListener {
        fun onItemClick(position: Int)
    }
}

