package com.example.nalassetmanagement.screen.asset_info.asset_location

import android.content.Context
import android.graphics.Color
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.nalassetmanagement.R
import com.example.nalassetmanagement.databinding.ItemLocationBinding
import com.example.nalassetmanagement.model.local.AssetLocation

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
                binding.tvLocation.text = formatString(context, this)
            }
        }
    }
    private fun formatString(context: Context, item: AssetLocation) : SpannableString {

        val locationInfo = context.getString(R.string.location_info, item.date, item.user, item.location)

        val spannableString = SpannableString(locationInfo)

        val startIndex1 = locationInfo.indexOf("Đã giao cho")
        val endIndex1 = startIndex1 + "Đã giao cho".length


        val startIndex3 = locationInfo.indexOf(item.location)
        val endIndex3 = startIndex3 + item.location.length

        spannableString.setSpan(
            ForegroundColorSpan(context.getColor(R.color.green)),
            startIndex1,
            endIndex1,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )


        spannableString.setSpan(
            ForegroundColorSpan(context.getColor(R.color.blue)),
            startIndex3,
            endIndex3,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        return spannableString
    }

}
