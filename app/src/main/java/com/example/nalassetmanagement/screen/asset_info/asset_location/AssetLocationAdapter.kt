package com.example.nalassetmanagement.screen.asset_info.asset_location

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.nalassetmanagement.R
import com.example.nalassetmanagement.databinding.ItemLocationBinding
import com.example.nalassetmanagement.model.AssetLocation

class AssetLocationAdapter(private val list: List<AssetLocation>) :
    Adapter<AssetLocationAdapter.AssetLocationViewHolder>() {

    class AssetLocationViewHolder(val binding: ItemLocationBinding) : ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AssetLocationViewHolder {
        val binding =
            ItemLocationBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AssetLocationViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: AssetLocationViewHolder, position: Int) {

        with(holder) {
            with(list[position]) {
                val context = binding.root.context
                binding.tvLocation.text = context.getString(R.string.location_info, date, user, location)
            }
        }
    }

}
