package com.example.nalassetmanagement.screen.asset_filter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.nalassetmanagement.databinding.ItemFilterBinding
import com.example.nalassetmanagement.model.AssetFilter

class AssetFilterAdapter(private val list: List<AssetFilter>, private val listener: OnClickListener) :
    Adapter<AssetFilterAdapter.AssetFilterViewHolder>() {

    class AssetFilterViewHolder(val binding: ItemFilterBinding) : ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AssetFilterViewHolder {
        val binding =
            ItemFilterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AssetFilterViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: AssetFilterViewHolder, position: Int) {

        with(holder) {
            with(list[position]) {
                binding.tvTitle.text = title
                binding.tvValue.text = value

                binding.root.setOnClickListener {
                    listener.onItemClick(position)
                }
            }
        }
    }

    interface OnClickListener {
        fun onItemClick(position: Int)
    }
}
