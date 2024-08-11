package com.example.nalassetmanagement.screen.asset_list

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.nalassetmanagement.R
import com.example.nalassetmanagement.databinding.ItemAssetBinding
import com.example.nalassetmanagement.model.local.ObjectFilter
import com.example.nalassetmanagement.model.server.Asset
import com.example.nalassetmanagement.model.server.KeyValue
import com.example.nalassetmanagement.view.extension.loadWithPicasso
import java.util.Locale

class AssetListAdapter(var list: List<Asset>, private var listener: AssetListEventListener) :
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
        listener.onAssetListChange(list)
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: AssetListViewHolder, itemPosition: Int) {
        with(holder) {
            with(list[itemPosition]) {
                binding.imgAssetAvatar.loadWithPicasso("", modelId, )
                binding.tvAssetName.text = name
                binding.tvUser.text = user?.userName ?: ""
                binding.tvAddress.text = address?.name ?: "Kosmo-TayHo"
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

            4 -> {
                return R.drawable.bg_tv_status_gray
            }

            5 -> {
                return R.drawable.bg_tv_status_green_dark
            }
            else -> {
                return R.drawable.bg_tv_status_green_dark
            }
        }
    }

    fun searchEditText(list: List<Asset>, text: String) {
        val temp: MutableList<Asset> = ArrayList()
        for (item in list) {
            val lDapName = item.user?.userName?.trim()?.lowercase(Locale.ROOT)
            val assetName = item.name?.trim()?.lowercase(Locale.ROOT)
            val textLowerCase = text.trim().lowercase(Locale.ROOT)
            if (lDapName?.contains(textLowerCase) == true || assetName?.contains(textLowerCase) == true) {
                temp.add(item)
            }
        }
        replaceList(temp)
    }

    fun filterList(
        list: List<Asset>,
        user: ObjectFilter?,
        category: ObjectFilter?,
        address: ObjectFilter?,
        producer: ObjectFilter?
    ) {

        val tempUser: MutableList<Asset> = ArrayList()
        for (item in list) {
            if (user?.id == 0) {
                tempUser.addAll(list)
                break
            } else if (user?.id == item.userId) {
                tempUser.add(item)
            }
        }

        val tempCategory: MutableList<Asset> = ArrayList()
        for (item in tempUser) {
            if (category?.id == 0) {
                tempCategory.addAll(tempUser)
                break
            } else if (category?.id == item.categoryId) {
                tempCategory.add(item)
            }
        }

        val tempAddress: MutableList<Asset> = ArrayList()
        for (item in tempCategory) {
            if (address?.id == 0) {
                tempAddress.addAll(tempCategory)
                break
            } else if (address?.id == item.addressId) {
                tempAddress.add(item)
            }
        }

        val tempProducer: MutableList<Asset> = ArrayList()
        for (item in tempAddress) {
            if (producer?.id == 0) {
                tempProducer.addAll(tempAddress)
                break
            } else if (producer?.id == item.producerId) {
                tempProducer.add(item)
            }
        }

        replaceList(tempProducer)
    }

    interface AssetListEventListener {
        fun onItemClick(position: Int)
        fun onAssetListChange(list: List<Asset>)
    }
}

